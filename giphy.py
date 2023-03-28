import requests
import logging
import json
import os
import requests
import datetime
import time
from PIL import Image, WebPImagePlugin, ImageSequence, ImageOps
from urllib.parse import urlparse
from collections import OrderedDict
import subprocess
import imageio
import imageio.v3 as iio
from PIL.Image import Resampling
from io import BytesIO
import os
from PIL import Image
from io import BytesIO
import shutil
from PIL import Image, ImageSequence
import io


class ImageList:
    def __init__(self, ordered_dict: OrderedDict):
        self.ordered_dict = ordered_dict


class ImageInfo:
    def __init__(self, url, name, width, height):
        self.url = url
        self.name = name
        self.width = width
        self.height = height


class ImageOriginal:
    def __init__(self, webp):
        self.webp = webp


class Images:
    def __init__(self, original: ImageOriginal):
        self.original = original


class Result:
    def __init__(self, id_str, title, images: Images, image_name):
        self.id_str = id_str
        self.title = title
        self.images = images
        self.image_name = image_name


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
        original = ImageOriginal(original_dict['webp'])
        images = Images(original)
        results.append(Result(id_str, title, images))
    # create a new TagResponse object with the parsed data
    tag_response = TagResponse(next_url, results)
    return tag_response


def download_result(result: Result, parent_fold):
    if result.images.original:
        # Get the file extension from the URL.
        parsed_link = urlparse(result.images.original.webp)
        path = parsed_link.path
        file_extension = path.split('.')[-1]
        # Generate a filename for the image using the file extension.
        filename = f'{result.image_name}.{file_extension}'
        download_image(result.images.original.webp, parent_fold, filename)
        image_resize(parent_fold, filename)


def download_tag_image(response: TagResponse, parent_fold):
    index = 0
    for result in response.results:
        result.image_name = index
        download_result(result, parent_fold)
        index = index + 1


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


def download_search(name, offset, parent_fold):
    url = f'https://api.giphy.com/v1/gifs/search?offset={offset}&type=gifs&sort=&q={name}&api_key' \
        f'=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id=1870447a7cdb48f4'
    print("download_search start name", name)
    download_type(url, parent_fold)
    print("download_search end name", name)


def download_type(url, parent_fold):
    print("download_type start name url = ", url)
    response_str = request_url(url)
    if response_str is not None:
        try:
            category_dict = json.loads(response_str)
            display_name = parent_fold
            print('download_type display name ', display_name)
            childrens_dict = category_dict.get('data')
            if len(childrens_dict) != 0:
                count = 0
                for result_dict in childrens_dict:
                    id_str = result_dict.get('id')
                    title = result_dict.get('title')
                    images_dict = result_dict.get('images')
                    original = get_meet_image_url(images_dict)
                    if original is not None:
                        images = Images(original)
                        result = Result(id_str, title, images, count)
                        download_result(result, parent_fold)
                        count = count + 1
                        if count > 50:
                            break
                print("download_type end")
            else:
                print("download_type len 0")
        except json.JSONDecodeError:
            print('download_type Invalid JSON string')
        except KeyError as e:
            print(f'download_type Missing property {e}')
    else:
        print("download_type error")


def get_meet_image_url(images_dict):
    if images_dict is not None:
        if 'original' in images_dict:
            original_dict = images_dict.get('original')
            if original_dict is not None:
                if 'webp' in original_dict:
                    return ImageOriginal(original_dict['webp'])
        if 'fixed_width' in images_dict:
            fixed_width = images_dict.get('fixed_width')
            if 'webp' in fixed_width:
                return ImageOriginal(fixed_width['webp'])
    return None


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
            if name is not None:
                download_search(name, 0, os.path.join(category, name))
                download_search(name, 25, os.path.join(category, name))
            else:
                print('download_category error', name)
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


def download_image(url, parent_fold, file_name):
    print('download_image ------- start ', url)
    parent_fold = os.path.join(parent_fold, 'origin')
    os.makedirs(parent_fold, exist_ok=True)
    # Combine the parent folder path and filename to create the full file path.
    file_path = os.path.join(parent_fold, file_name)

    if os.path.exists(file_path):
        print(f"Image already exists locally: {file_path}")
        return True
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
                    return True
            except requests.exceptions.RequestException as e:
                # 发生异常时记录日志并重试
                logging.error(f'download_image Request failed: {str(e)}. Retry {retries + 1}/{MAX_RETRIES}')
                retries += 1
                time.sleep(5)  # 等待5秒后重试
        # 重试多次后仍然失败，记录日志并返回None
        logging.error(f'download_image Request failed after image name {file_path} {MAX_RETRIES} retries: {url}')
        return False


def download_related():
    list_gif_item = [
        {
            "key": "Hello",
            "gif_id": "BElb9DVpHezcZufOhl",
            "type": "gifs"
        },
        {
            "key": "Hello Cartoon1",
            "gif_id": "noyBeNjH4nbtXV5ZLA",
            "type": "gifs"
        },
        {
            "key": "Hello Cartoon2",
            "gif_id": "1kJxyyCq9ZHXX0GM3a",
            "type": "gifs"
        },
        {
            "key": "Hello animal1",
            "gif_id": "llJVg4Ri0VrUBzNOgG",
            "type": "gifs"
        },
        {
            "key": "Hello animal2",
            "gif_id": "PgdWZV8Bb1fFqVcmtk",
            "type": "gifs"
        },
        {
            "key": "Hello cute",
            "gif_id": "E1w0yvMxBIv5M8WkL8",
            "type": "gifs"
        },
        {
            "key": "Hello friend",
            "gif_id": "3oz8xSjBmD1ZyELqW4",
            "type": "gifs"
        },
        {
            "key": "goodbye cute",
            "gif_id": "UUhnOExaUB8BkDUaJn",
            "type": "gifs"
        },
        {
            "key": "goodbye cartoon",
            "gif_id": "3o752gFScyte7n6fCM",
            "type": "gifs"
        },
        {
            "key": "goodbye Simpson",
            "gif_id": "l2Je9a1y33ZjSDSeY",
            "type": "gifs"
        },
        {
            "key": "goodbye friend",
            "gif_id": "UQaRUOLveyjNC",
            "type": "gifs"
        },
        {
            "key": "goodbye sticker",
            "gif_id": "kdEl6JgqpAvT0QonPd",
            "type": "gifs"
        },
        {
            "key": "goodbye cat",
            "gif_id": "iPiUxztIL4Sl2",
            "type": "gifs"
        },
        {
            "key": "Tom and jerry01",
            "gif_id": "y9QemIlaYYWdi",
            "type": "gifs"
        },
        {
            "key": "Tom and jerry02",
            "gif_id": "OpiqxxEmyi4zC",
            "type": "gifs"
        },
        {
            "key": "Tom and jerry03",
            "gif_id": "13Qumr2SLqrl5e",
            "type": "gifs"
        },
        {
            "key": "Crayon Shin-chan01",
            "gif_id": "W1qqdGdnQhR2JorbbQ",
            "type": "gifs"
        },
        {
            "key": "Crayon Shin-chan02",
            "gif_id": "3ohjUWt3CrcibB7R3W",
            "type": "gifs"
        },
        {
            "key": "Crayon Shin-chan03",
            "gif_id": "xUNda1aXN8zSrNartK",
            "type": "gifs"
        },
        {
            "key": "SpongeBob SquarePants01",
            "gif_id": "SKGo6OYe24EBG",
            "type": "gifs"
        },
        {
            "key": "SpongeBob SquarePants02",
            "gif_id": "129OnZ9Qn2i0Ew",
            "type": "gifs"
        }
    ]
    index = 0
    for gif_item in list_gif_item:
        gif_id = gif_item.get('gif_id')
        gif_name = gif_item.get('key')
        url = f'https://api.giphy.com/v1/gifs/related?gif_id={gif_id}&api_key=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g' \
            f'&pingback_id=1870d4cd3af20c73 '
        print(f'download_related start {gif_name}')
        download_type(url, os.path.join("related", f"{gif_name}"))
        index = index + 1
        print(f'download_related end {gif_name}')


def image_resize(parent_fold, file_name):
    # 打开webp动图文件
    origin_url = os.path.join(parent_fold, 'origin', file_name)
    if os.path.exists(origin_url):
        print('image_resize origin_url exist')
    else:
        print(f"image_resize origin_url not exists : {origin_url}")
        return False
    resize_fold = os.path.join(parent_fold, 'resize')
    resize_url = os.path.join(resize_fold, file_name)
    os.makedirs(resize_fold, exist_ok=True)
    # if os.path.exists(resize_url):
    #     print(f"image_resize already exists locally: {resize_url}")
    #     return True
    print(f'image_resize resize_url  {resize_url}')
    # 循环遍历每一帧
    with Image.open(origin_url) as im_pillow:
        frames = []
        for frame in ImageSequence.Iterator(im_pillow):
            # 裁剪图片，使其等比例缩放并填充至512x512大小
            width, height = frame.size
            ratio = min(512 / width, 512 / height)
            new_size = (int(width * ratio), int(height * ratio))
            print(f"image_resize {new_size}")
            # 创建一个全透明的512x512大小的图片，将裁剪后的图片粘贴到居中位置
            background = Image.new('RGBA', (512, 512), (0, 0, 0, 0))
            paste_pos = ((512 - new_size[0]) // 2, (512 - new_size[1]) // 2)
            frame = frame.resize(new_size, resample=Image.LANCZOS)
            background.paste(frame, paste_pos)
            # 将原始帧的duration信息传递给处理后的帧
            if 'duration' in frame.info:
                background.info['duration'] = frame.info['duration']
            # 将每一帧添加到帧列表中
            frames.append(background)
        # 保存图片
        frames[0].save(resize_url, format='webp', save_all=True,
                       append_images=frames[1:])
    return True


def image_zip(parent_fold, file_name):
    resize_url = os.path.join(parent_fold, 'resize', file_name)
    zip_fold = os.path.join(parent_fold, 'compressed')
    zip_url = os.path.join(zip_fold, file_name)
    if os.path.exists(resize_url):
        print("image_zip")
    else:
        print(f"image_zip not exists : {resize_url}")
        return False
    os.makedirs(os.path.join(zip_fold), exist_ok=True)
    if os.path.exists(zip_url):
        print(f"image_zip already exists locally: {zip_url}")
        # return True
    if 'file_name' == '0.webp':
        compress_webp_animation(resize_url, zip_url)
    print("image_zip end")
    return True


def image_tray(parent_fold, file_name):
    zip_url = os.path.join(parent_fold, 'compressed', file_name)
    tray_url = os.path.join(parent_fold, 'compressed', "tray.png")
    if os.path.exists(zip_url):
        print('image_tray zip_url exist')
    else:
        print(f"image_tray zip_url not exists : {zip_url}")
        return False
        # 打开webp动画文件
    with Image.open(zip_url) as im:
        # 获取第一帧图片
        first_frame = im.copy()
        # 调整大小为96x96
        resized_image = first_frame.resize((96, 96))
        # 保存为PNG格式图片
        resized_image.save(tray_url, "PNG")
    return True


def save_webp_frames(input_path, output_prefix):
    # 打开webp动图文件
    with open(input_path, "rb") as f:
        data = f.read()

    # 打开动图并获取其帧
    im = Image.open(io.BytesIO(data))
    for i, frame in enumerate(ImageSequence.Iterator(im)):
        # 将每一帧保存为webp静图
        frame.save(os.path.join(output_prefix, 'frame' + str(i) + ".webp"), "webp")


size_limit = 500 * 1024;


def compress_webp_animation(input_path, output_path):
    # Open the input file as an image
    with Image.open(input_path) as im:
        # Extract all frames from the image
        frames = []
        try:
            while True:
                frames.append(im.copy())
                im.seek(len(frames))
        except EOFError:
            pass
        origin_size = os.path.getsize(input_path)
        if origin_size > size_limit:
            compressed_frames = []
            quality = int((size_limit / float(origin_size)) * 60)
            index = 0
            for frame in frames:
                output = BytesIO()
                frame.save(output, format='webp', lossless=False)
                index = index + 1
                # while output.tell() > 500 * 1024 / len(frames):
                #     print(f"compress_webp_animation {index} {int(500 * 1024 / len(frames))} tell = {output.tell()}")
                #     if quality < 5:
                #         break
                #     quality -= 5
                #     output = BytesIO()
                #     frame.save(output, format='webp', quality=quality, lossless=False)
                compressed_frames.append(Image.open(BytesIO(output.getvalue())))
                # Save the compressed frames as an animated webp file
                compressed_frames[0].save(output_path, quality=quality, lossless=False, optimize=False, save_all=True,
                                          append_images=compressed_frames[1:])
        else:
            shutil.copy(input_path, output_path)

    # # Compress the entire animated webp file until its size is less than 500KB
    # with open(output_path, 'rb') as f:
    #     compressed_data = f.read()
    #     while len(compressed_data) > 500 * 1024:
    #         quality -= 5
    #         compressed_data = Image.open(BytesIO(compressed_data)).save(None, 'webp', quality=quality, lossless=True)
    #
    # with open(output_path, 'wb') as f:
    #     f.write(compressed_data)


def main():
    # download_categories()
    # download_related()

    image_resize('./related/0', '0.webp')
    image_zip('./related/0', '0.webp')
    image_tray('./related/0', '0.webp')


main()
