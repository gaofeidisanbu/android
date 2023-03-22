import requests
import logging
import json
import os
import requests
import datetime
import time


class ImageOriginal:
    def __init__(self, url, webp):
        self.url = url
        self.webp = webp


class Images:
    def __init__(self, original: ImageOriginal):
        self.original = original


class Result:
    def __init__(self, id_str, title, images: Images):
        self.id_str = id_str
        self.title = title
        self.images = images


class TagResponse:
    def __init__(self, next_url, results: list):
        self.next_url = next_url
        self.results = results


def request_url(url, timeout=60):
    try:
        response = requests.get(url, timeout=timeout)
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
        id_str = result_dict.get('id')
        title = result_dict.get('title')
        images_dict = result_dict.get('images')
        original_dict = images_dict.get('original')
        original = ImageOriginal(original_dict['url'], original_dict['webp'])
        images = Images(original)
        results.append(Result(id_str, title, images))
    # create a new TagResponse object with the parsed data
    tag_response = TagResponse(next_url, results)
    return tag_response


def download_result(result: Result, parent_fold):
    if result.images.original:
        # download_image(result.images.original.url, os.path.join(parent_fold, result.id_str, 'original'))
        download_image(result.images.original.webp, os.path.join(parent_fold, result.id_str, 'original'), 'webp')


def download_tag_image(response: TagResponse, parent_fold):
    for result in response.results:
        download_result(result, parent_fold)


def download_image(url, parent_fold, file_name):
    print('download_image ------- start ', url)
    start_time = datetime.datetime.now()  # 获取当前时间
    os.makedirs(parent_fold, exist_ok=True)

    # Get the file extension from the URL.
    file_extension = file_name

    # Generate a filename for the image using the file extension.
    filename = f'image.{file_extension}'

    # Combine the parent folder path and filename to create the full file path.
    file_path = os.path.join(parent_fold, filename)

    if os.path.exists(file_path):
        print(f"Image already exists locally: {file_path}")
    else:
        # Make a request to the URL to get the image data.
        response = requests.get(url)
        # Write the image data to the file.
        with open(file_path, 'wb') as file:
            file.write(response.content)
    end_time = datetime.datetime.now()  # 获取下载结束时间
    delta_time = end_time - start_time  # 计算时间差
    print('download_image ------- delta_time ', delta_time.seconds)
    print('download_image ------- end ', url)


def download_tag_2(url, parent_fold):
    print('download_tag_2 start ', url)
    response_str = request_url(url)
    response = parse_image_tag(response_str)
    download_tag_image(response, parent_fold)
    print('download_tag_2 end ', url)
    return response


max_count = 50


def download_tag(url, parent_fold):
    print('download_tag start ', url)
    count = 0
    i = 0
    while i < 10:
        response = download_tag_2(url, parent_fold)
        count = count + len(response.results)
        if response is None or response.next_url is None or count > max_count:
            break
        i += 1
    print('download_tag end ', url)


def download_category(url):
    print("download_category start", url)
    response_str = request_url(url)
    category_dict = json.loads(response_str)
    display_name = category_dict.get("display_name")
    print('download_category display name ', display_name)
    childrens_dict = category_dict.get('children')
    count = 0
    for children_dict in childrens_dict:
        children_id = children_dict.get('id')
        children_name = children_dict.get('display_name')
        children_slug = children_dict.get('slug')
        children_url = f"https://giphy.com/api/v4/channels/{children_id}/feed/"
        print('download_category children name ', children_name)
        download_tag(children_url, os.path.join(display_name, children_name))
        count = count + 1
        if count > 100:
            break
    print("download_category end", url)


def download_emotion_param(url, parent_fold):
    print("download_emotion start", url)
    response_str = request_url(url)
    category_dict = json.loads(response_str)
    display_name = parent_fold
    print('download_emotion display name ', display_name)
    childrens_dict = category_dict.get('data')
    count = 0
    for result_dict in childrens_dict:
        id_str = result_dict.get('id')
        title = result_dict.get('title')
        images_dict = result_dict.get('images')
        original_dict = images_dict.get('original')
        original = ImageOriginal(original_dict['url'], original_dict['webp'])
        images = Images(original)
        result = Result(id_str, title, images)
        download_result(result, parent_fold)
        count = count + 1
        if count > 50:
            break
    print("download_category end", url)


def download_emotion():
    emotion_list = ['reaction', 'love', 'happy', 'sad', 'excited', 'angry', 'shocked', 'hungry', 'scared', 'tired', 'surprised',
            'drunk', 'bored', 'sassy', 'frustrated', 'nervous', 'pain', 'sick', 'disappointed', 'lonely', 'stressed',
            'suspicious', 'embarrassed', 'unimpressed', 'relaxed', 'inspired']
    for name in emotion_list:
        download_emotion_param(f'https://api.giphy.com/v1/gifs/search?offset=0&type=gifs&sort=&q={name}&api_key'
                     '=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id=1870447a7cdb48f4', os.path.join("emotion", name))
        download_emotion_param(f'https://api.giphy.com/v1/gifs/search?offset=25&type=gifs&sort=&q={name}&api_key'
                               '=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id=1870447a7cdb48f4',
                               os.path.join("emotion", name))


def main():
    download_emotion()
    # download_category('https://giphy.com/api/v4/channels/11267284/')


main()
