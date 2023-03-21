import requests
import logging
import json
import os
import requests

class ImageOriginal:
    def __init__(self, url, webp):
        self.url = url
        self.webp = webp


class Images:
    def __init__(self, original: ImageOriginal):
        self.original = original


class Result:
    def __init__(self, title, images: Images):
        self.title = title
        self.images = images


class TagResponse:
    def __init__(self, next_url, results):
        self.next_url = next_url
        self.results = results


def request_url(url):
    try:
        response = requests.get(url)
        return response.text
    except Exception as e:
        logging.error(f"An error occurred while requesting {url}: {e}")


def parse_image_tag(tag_str):
    # parse the JSON string into a dictionary
    tag_dict = json.loads(tag_str)

    # extract the 'next' and 'images' fields from the dictionary
    next_url = tag_dict.get('next')
    results_dict = tag_dict.get('results')
    results = []
    for result_dict in results_dict:
        # create a list of Image objects from the 'images' di21ctionary
        title = result_dict.get('title')
        images_dict = result_dict.get('images')
        original_dict = images_dict.get('original')
        original = ImageOriginal(original_dict['url'], original_dict['webp'])
        images = Images(original)
        results.append(Result(title, images))
    # create a new TagResponse object with the parsed data
    tag_response = TagResponse(next_url, results)
    return tag_response


def download_result(result: Result, parent_fold):
    if result.images.original:
        download_image(result.images.original.url, os.path.join(parent_fold, 'original'))
        download_image(result.images.original.webp, os.path.join(parent_fold, 'original'))


def download_tag_image(response: TagResponse, parent_fold):
    for result in response.results:
        download_result(result, parent_fold)


def download_image(url, parent_fold):
    os.makedirs(parent_fold, exist_ok=True)
    # Make a request to the URL to get the image data.
    response = requests.get(url)

    # Get the file extension from the URL.
    file_extension = url.split('.')[-1]

    # Generate a filename for the image using the file extension.
    filename = f'image.{file_extension}'

    # Combine the parent folder path and filename to create the full file path.
    file_path = os.path.join(parent_fold, filename)

    # Write the image data to the file.
    with open(file_path, 'wb') as file:
        file.write(response.content)


def main():
    response_str = request_url('https://giphy.com/api/v4/channels/11313767/feed/?offset=0')
    result = parse_image_tag(response_str)
    download_tag_image(result, './gif')
    print(result)


main()

