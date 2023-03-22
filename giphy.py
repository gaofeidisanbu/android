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


def request_url(url, timeout=600):
    # 设置日志格式
    logging.basicConfig(format='%(asctime)s %(levelname)s %(message)s', level=logging.INFO)

    # 设置重试次数
    MAX_RETRIES = 3
    retries = 0
    while retries < MAX_RETRIES:
        try:
            # 发送请求并设置超时时间
            start_time = datetime.datetime.now()  # 获取当前时间
            response = requests.get(url, timeout=timeout)
            end_time = datetime.datetime.now()  # 获取下载结束时间
            delta_time = end_time - start_time  # 计算时间差
            print('request_url ------- delta_time ', delta_time.seconds)
            print('request_url ------- end ', url)
            # 如果返回码为200，表示请求成功，返回响应内容
            if response.status_code == 200:
                return response.content
        except requests.exceptions.RequestException as e:
            # 发生异常时记录日志并重试
            logging.error(f'Request failed: {str(e)}. Retry {retries + 1}/{MAX_RETRIES}')
            retries += 1
            time.sleep(5)  # 等待5秒后重试
    # 重试多次后仍然失败，记录日志并返回None
    logging.error(f'Request failed after {MAX_RETRIES} retries: {url}')
    return None


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
        # 设置日志格式
        logging.basicConfig(format='download_image %(asctime)s %(levelname)s %(message)s', level=logging.INFO)
        # 设置重试次数
        MAX_RETRIES = 3
        retries = 0
        for i in range(MAX_RETRIES):
            try:
                start_time = datetime.datetime.now()  # 获取当前时间
                # 发送请求并设置超时时间
                response = requests.get(url, timeout=600)
                # 如果返回码为200，表示请求成功，返回响应内容
                if response.status_code == 200:
                    # Write the image data to the file.
                    with open(file_path, 'wb') as file:
                        file.write(response.content)
                    end_time = datetime.datetime.now()  # e 获取下载结束时间
                    delta_time = end_time - start_time  # 计算时间差
                    print('download_image ------- delta_time ', delta_time.seconds)
                    print('download_image ------- end ', url)
                    return
            except requests.exceptions.RequestException as e:
                # 发生异常时记录日志并重试
                logging.error(f'download_image Request failed: {str(e)}. Retry {retries + 1}/{MAX_RETRIES}')
                retries += 1
                time.sleep(5)  # 等待5秒后重试
        # 重试多次后仍然失败，记录日志并返回None
        logging.error(f'download_image Request failed after image name {file_path} {MAX_RETRIES} retries: {url}')


def download_tag_2(url, parent_fold):
    print('download_tag_2 start ', url)
    response_str = request_url(url)
    if response_str is not None:
        response = parse_image_tag(response_str)
        download_tag_image(response, parent_fold)
        print('download_tag_2 end ', url)
    else:
        print('download_tag_2 end error', url)

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


def download_artists(url):
    print("download_category start", url)
    response_str = request_url(url)
    if response_str is not None:
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
    else:
        print("download_category error", url)


def download_search(key, offset, parent_fold):
    url = f'https://api.giphy.com/v1/gifs/search?offset={offset}&type=gifs&sort=&q={key}&api_key' \
        f'=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id=1870447a7cdb48f4'
    print("download_emotion key", key)
    response_str = request_url(url)
    if response_str is not None:
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
        print("download_category key", key)
    else:
        print("download_category error", key)


def download_emotion():
    emotion_list = ['reaction', 'love', 'happy', 'sad', 'excited', 'angry', 'shocked', 'hungry', 'scared', 'tired',
                    'surprised',
                    'drunk', 'bored', 'sassy', 'frustrated', 'nervous', 'pain', 'sick', 'disappointed', 'lonely',
                    'stressed',
                    'suspicious', 'embarrassed', 'unimpressed', 'relaxed', 'inspired']
    for name in emotion_list:
        download_search(0, name, os.path.join("emotion", name))
        download_search(25, name, os.path.join("emotion", name))


def download_category(url, category):
    print("download_category start", category)
    response_str = request_url(url)
    if response_str is not None:
        response_dict = json.loads(response_str)
        datas_dict = response_dict.get('data')
        for data in datas_dict:
            name = data.get('name_encoded')
            download_search(name, 0, os.path.join(category, name))
            download_search(name, 25, os.path.join(category, name))
    print("download_category end", category)


def download_categories():
    print("download_categories start")
    url = 'https://api.giphy.com/v1/gifs/categories?api_key=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id' \
          '=186fda3de4e75f6b '
    response_str = request_url(url)
    if response_str is not None:
        response_dict = json.loads(response_str)
        datas_dict = response_dict.get('data')
        for data in datas_dict:
            category = data.get('name_encoded')
            url = f'https://api.giphy.com/v1/gifs/categories/{category}?api_key=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g' \
                f'&pingback_id=186fda3de4e75f6b '
            download_category(url, category)
    print("download_animal end")


def main():
    # download_emotion()
    download_categories()
    # download_category('https://giphy.com/api/v4/channels/11267284/')


main()
