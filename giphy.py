import datetime
import io
import json
import logging
import os
import shutil
import time
from collections import OrderedDict
from urllib.parse import urlparse
import colorlog
import requests
from PIL import Image, ImageSequence

# 创建logging实例
logger = logging.getLogger()
logger.setLevel(logging.INFO)

# 定义控制台输出格式
formatter = colorlog.ColoredFormatter(
    "%(log_color)s%(asctime)s %(log_color)s%(levelname)-8s%(reset)s %(log_color)s%(message)s",
    log_colors={
        'DEBUG': 'cyan',
        'INFO': 'green',
        'WARNING': 'yellow',
        'ERROR': 'red',
        'CRITICAL': 'red,bg_white',
    },
    secondary_log_colors={},
    style='%'
)

# 创建控制台处理器
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.INFO)
console_handler.setFormatter(formatter)
# 将控制台处理器添加到logger
logger.addHandler(console_handler)

log_file = 'log_file.log'

os.remove(log_file)
file_handler = logging.FileHandler(log_file)
file_handler.setLevel(logging.INFO)
formatter_file = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
file_handler.setFormatter(formatter_file)
# 将控制台处理器添加到logger
logger.addHandler(file_handler)


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

    def __init__(self, webp=None, gif=None):
        self.webp = webp
        self.gif = gif


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
            logger.info(f'request_url ------- delta_time {delta_time.seconds} {url}')
            logger.info('request_url ------- end')
            # 如果返回码为200，表示请求成功，返回响应内容
            if response.status_code == 200:
                return response.content
        except requests.exceptions.RequestException as e:
            # 发生异常时记录日志并重试
            logging.error(f'Request failed: {str(e)}. Retry {retries + 1}/{MAX_RETRIES}')
            retries += 1
            time.sleep(5)  # 等待5秒后重试
    # 重试多次后仍然失败，记录日志并返回None
    logger.error(f'Request failed after {MAX_RETRIES} retries: {url}')
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


def download_webp(result: Result, parent_fold):
    if result.images.original:
        # Get the file extension from the URL.
        parsed_link = urlparse(result.images.original.webp)
        path = parsed_link.path
        file_extension = path.split('.')[-1]
        # Generate a filename for the image using the file extension.
        filename = f'{result.image_name}.{file_extension}'

        download_output_fold = os.path.join(parent_fold, 'origin')
        download_image(result.images.original.webp, download_output_fold, filename)

        resize_input_file = os.path.join(download_output_fold, filename)
        resize_output_fold = os.path.join(parent_fold, 'resize')
        image_resize(resize_input_file, resize_output_fold, filename, 512, 512)

        zip_input_file = os.path.join(resize_output_fold, filename)
        zip_output_fold = os.path.join(parent_fold, 'compressed')
        is_zip_success = image_zip(zip_input_file, zip_output_fold, filename)

        if is_zip_success:
            tray_input_file = os.path.join(zip_output_fold, filename)
            tray_output_file = os.path.join(zip_output_fold, "tray.png")
            image_tray(tray_input_file, tray_output_file, 50 * 1024)

            resize_240_input_file = os.path.join(zip_output_fold, filename)
            resize_240_output_fold = os.path.join(parent_fold, 'compressed_240')
            image_resize(resize_240_input_file, resize_240_output_fold, filename, 240, 240)


def download_gif(result: Result, parent_fold):
    if result.images.original:
        # Get the file extension from the URL.
        parsed_link = urlparse(result.images.original.gif)
        path = parsed_link.path
        file_extension = path.split('.')[-1]
        # Generate a filename for the image using the file extension.
        filename = f'{result.image_name}.{file_extension}'
        output_fold = os.path.join(parent_fold, 'gif')
        download_image(result.images.original.gif, output_fold, filename)


def download_tag_image(response: TagResponse, parent_fold):
    index = 0
    for result in response.results:
        result.image_name = index
        download_webp(result, parent_fold)
        index = index + 1


def download_tag_2(url, parent_fold):
    logger.info(f'download_tag_2 start {url}')
    response_str = request_url(url)
    if response_str is not None:
        response = parse_image_tag(response_str)
        download_tag_image(response, parent_fold)
        logger.info('download_tag_2 end ')
    else:
        logger.info('download_tag_2 end error')

    return response


max_count = 50


def download_tag(url, parent_fold):
    logger.info(f'download_tag start {url}')
    count = 0
    i = 0
    while i < 10:
        response = download_tag_2(url, parent_fold)
        count = count + len(response.results)
        if response is None or response.next_url is None or count > max_count:
            break
        i += 1
    logger.info('download_tag end ')


def download_artists(url):
    logger.info(f"download_category start {url}")
    response_str = request_url(url)
    if response_str is not None:
        category_dict = json.loads(response_str)
        display_name = category_dict.get("display_name")
        logger.info(f'download_category display name {display_name}')
        childrens_dict = category_dict.get('children')
        count = 0
        for children_dict in childrens_dict:
            children_id = children_dict.get('id')
            children_name = children_dict.get('display_name')
            children_slug = children_dict.get('slug')
            children_url = f"https://giphy.com/api/v4/channels/{children_id}/feed/"
            logger.info('download_category children name ', children_name)
            download_tag(children_url, os.path.join(display_name, children_name))
            count = count + 1
            if count > 100:
                break
        logger.info("download_category end")
    else:
        logger.info("download_category error")


def download_search(name, offset, parent_fold):
    url = f'https://api.giphy.com/v1/gifs/search?offset={offset}&type=gifs&sort=&q={name}&api_key' \
        f'=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g&pingback_id=1870447a7cdb48f4'
    logger.info(f"download_search start name {name} {offset} {url}")
    download_type(url, parent_fold, offset)
    logger.info(f"download_search end name {name}")


def download_type(url, parent_fold, start_index):
    logger.info(f"download_type start name url = {url}")
    response_str = request_url(url)
    if response_str is not None:
        try:
            category_dict = json.loads(response_str)
            display_name = parent_fold
            logger.info(f'download_type display name {display_name}', )
            childrens_dict = category_dict.get('data')
            if len(childrens_dict) != 0:
                count = 0
                for result_dict in childrens_dict:
                    id_str = result_dict.get('id')
                    title = result_dict.get('title')
                    images_dict = result_dict.get('images')
                    tags_dict = result_dict.get('tags')
                    original = get_meet_image_webp_url(images_dict)
                    if original is not None:
                        images = Images(original)
                        result = Result(id_str, title, images, count + start_index)
                        download_webp(result, parent_fold)
                        download_gif(result, parent_fold)
                        download_tags(tags_dict, parent_fold, result.image_name)
                        count = count + 1
                        if count > 200:
                            break
                logger.info("download_type end")
            else:
                logger.error("download_type len 0")
        except json.JSONDecodeError:
            logger.error('download_type Invalid JSON string')
        except KeyError as e:
            logger.error(f'download_type Missing property {e}')
    else:
        logger.error("download_type error")


def get_meet_image_webp_url(images_dict):
    image_original = ImageOriginal()
    if images_dict is not None:
        if 'original' in images_dict:
            original_dict = images_dict.get('original')
            if original_dict is not None:
                if image_original.webp is None and 'webp' in original_dict:
                    image_original.webp = original_dict['webp']
                if image_original.gif is None and 'url' in original_dict:
                    url = original_dict['url']
                    if check_image_type(url, 'gif'):
                        image_original.gif = url

        if image_original.webp is None and 'fixed_width' in images_dict:
            fixed_width = images_dict.get('fixed_width')
            if 'webp' in fixed_width:
                image_original.webp = fixed_width['webp']
    return image_original


def check_image_type(url, image_type):
    parsed_link = urlparse(url)
    path = parsed_link.path
    file_extension = path.split('.')[-1]
    if file_extension == image_type:
        return True
    return False


def download_category(url, category):
    logger.info(f"download_category start {category}")
    response_str = request_url(url)
    if response_str is not None:
        try:
            response_dict = json.loads(response_str)
            datas_dict = response_dict.get('data')
            for data in datas_dict:
                name = data.get('name_encoded')
                if name is not None:
                    download_search(name, 0, os.path.join('sticker', category, name))
                    download_search(name, 25, os.path.join('sticker', category, name))
                    download_search(name, 50, os.path.join('sticker', category, name))
                    download_search(name, 75, os.path.join('sticker', category, name))
                    download_search(name, 100, os.path.join('sticker', category, name))
                    download_search(name, 125, os.path.join('sticker', category, name))
                    download_search(name, 150, os.path.join('sticker', category, name))
                    download_search(name, 175, os.path.join('sticker', category, name))
                    download_search(name, 200, os.path.join('sticker', category, name))
                    break
                else:
                    logger.error(f'download_category error {name}')
        except Exception as e:
            logger.error(e)
    else:
        logger.error(f'error {category} {url}')
    logger.info(f"download_category end {category}")


def download_categories():
    logger.info("download_categories start")
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
    else:
        logger.error(f'error {url}')
    logger.info("download_animal end")


def download_image(url, output_fold, file_name):
    logger.info(f'download_image ------- start {url}')
    os.makedirs(output_fold, exist_ok=True)
    # Combine the parent folder path and filename to create the full file path.
    file_path = os.path.join(output_fold, file_name)

    if os.path.exists(file_path):
        logger.warning(f"download_image exists locally: {file_path}")
        return True
    else:
        # Make a request to the URL to get the image data.
        # 设置日志格式
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
                    logger.info(f'download_image ------- delta_time {delta_time.seconds}')
                    logger.info('download_image ------- end ')
                    return True
            except requests.exceptions.RequestException as e:
                # 发生异常时记录日志并重试
                logger.error(f'download_image Request failed: {str(e)}. Retry {retries + 1}/{MAX_RETRIES}')
                retries += 1
                time.sleep(5)  # 等待5秒后重试
            except Exception as e:
                logger.error(e)
        # 重试多次后仍然失败，记录日志并返回None
        logger.error(f'download_image Request failed after image name {file_path} {MAX_RETRIES} retries: {url}')
        return False


def download_related():
    list_gif_item = [
    ]
    index = 0
    for gif_item in list_gif_item:
        gif_id = gif_item.get('gif_id')
        gif_name = gif_item.get('key')
        url = f'https://api.giphy.com/v1/gifs/related?gif_id={gif_id}&api_key=Gc7131jiJuvI7IdN0HZ1D7nh0ow5BU6g' \
            f'&pingback_id=1870d4cd3af20c73 '
        logger.info(f'download_related start {gif_name}')
        download_type(url, os.path.join('sticker', "related", f"{gif_name}"))
        index = index + 1
        logger.info(f'download_related end {gif_name}')


def image_resize(input_file, output_fold, file_name, target_width, target_height):
    # 打开webp动图文件
    logger.info(f'image_resize start {input_file}')
    if os.path.exists(input_file):
        logger.info('image_resize input_file exist')
    else:
        logger.error(f"image_resize input_file not exists : {input_file}")
        return False
    resize_url = os.path.join(output_fold, file_name)
    os.makedirs(output_fold, exist_ok=True)
    if os.path.exists(resize_url):
        logger.warning(f"image_resize already exists locally: {resize_url}")
    # 循环遍历每一帧
    try:
        with Image.open(input_file) as im_pillow:
            frames = []
            for frame in ImageSequence.Iterator(im_pillow):
                # 裁剪图片，使其等比例缩放并填充至512x512大小
                width, height = frame.size
                ratio = min(target_width / width, target_height / height)
                new_size = (int(width * ratio), int(height * ratio))
                # 创建一个全透明的512x512大小的图片，将裁剪后的图片粘贴到居中位置
                background = Image.new('RGBA', (target_width, target_height), (0, 0, 0, 0))
                paste_pos = ((target_width - new_size[0]) // 2, (target_height - new_size[1]) // 2)
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
    except Exception as e:
        logger.error(e)
    logger.info(f'image_resize end {resize_url}')
    return True


size_limit = 500 * 1024


def image_zip(input_file, output_fold, file_name):
    logger.info(f"image_zip start {input_file}")
    zip_url = os.path.join(output_fold, file_name)
    if os.path.exists(input_file):
        logger.info(f"image_zip {input_file} exists")
    else:
        logger.error(f"image_zip input_file not exists : {input_file}")
        return False
    os.makedirs(os.path.join(output_fold), exist_ok=True)
    if os.path.exists(zip_url):
        logger.warning(f"image_zip already exists locally: {zip_url}")
    is_success = compress_webp_animation(input_file, zip_url)
    logger.info(f"image_zip end {is_success}")
    return is_success


def compress_webp_animation(input_path, output_path):
    logger.info('compress_webp_animation start')
    origin_size = os.path.getsize(input_path)
    if origin_size > size_limit:
        compress_webp_animation2(input_path, output_path)
    else:
        shutil.copy(input_path, output_path)
        logger.info('compress_webp_animation not zip')

    if validate_sticker(output_path):
        is_success = True
        logger.info('compress_webp_animation validate success')
    else:
        os.remove(output_path)
        is_success = False
        logger.error('compress_webp_animation validate fail')
    logger.info('compress_webp_animation end')
    return is_success


def compress_webp_animation2(input_path, output_path):
    compress_webp_animation3(input_path, output_path)


def compress_webp_animation3(input_path, output_path):
    input_size = os.path.getsize(input_path)
    logger.info(f'compress_webp_animation3 start zip {input_path} {input_size}')
    quality = int((size_limit / float(input_size)) * 80)
    try:
        with Image.open(input_path) as im:
            # Extract all frames from the image
            frames = []
            try:
                while True:
                    frames.append(im.copy())
                    im.seek(len(frames))
            except EOFError:
                pass
                is_continue = True
                while is_continue:
                    quality = quality - 5
                    if quality > 100:
                        quality = 100
                        is_continue = False
                    if quality < 1:
                        quality = 1
                        is_continue = False
                    frames[0].save(output_path, quality=quality, lossless=False, optimize=False,
                                   save_all=True,
                                   append_images=frames[1:])
                    logger.info(
                        f'compress_webp_animation3 end zip {output_path} {quality} {os.path.getsize(output_path)}')
                    if os.path.getsize(output_path) < size_limit:
                        return True
                    if is_continue is False:
                        return False
    except Exception as e:
        logger.error(e)


def image_tray(input_file, output_file, max_size):
    logger.info(f'image_tray start {input_file}')
    if os.path.exists(input_file):
        logger.info('image_tray zip_url exist')
    else:
        logger.error(f"image_tray zip_url not exists : {input_file}")
        return False
    if os.path.exists(output_file):
        logger.warning(f'image_tray {output_file} exist')
        return True
    try:
        # 打开webp动画文件
        with Image.open(input_file) as im:
            # 获取第一帧图片
            first_frame = im.copy()
            # 调整大小为96x96
            resized_image = first_frame.resize((96, 96))
            # 保存为PNG格式图片
            resized_image.save(output_file, "PNG", optimize=True, compress_level=9)
            # Check if the file size is within the specified limit
            if os.path.getsize(output_file) > max_size:
                # If the file size is larger than the specified limit, try to reduce it by reducing the compression
                # level
                for compress_level in range(8, 0, -1):
                    logger.info(f'image_tray compress before {os.path.getsize(output_file)}')
                    im.save(output_file, format='PNG', optimize=True, compress_level=compress_level)
                    logger.info(f'image_tray compress after {os.path.getsize(output_file)}')
                    if os.path.getsize(output_file) / 1024 <= max_size:
                        break
    except Exception as e:
        logger.error(e)
    if os.path.getsize(output_file) > max_size:
        os.remove(output_file)
        logger.warning('image_tray remove')
        return False
    logger.info(f'image_tray end {output_file} {os.path.getsize(output_file)}')
    return True


def download_tags(tags_dict, parent_fold, image_name):
    logger.info(f'download_tags start')
    output_fold = os.path.join(parent_fold, 'tag')
    output_file = os.path.join(output_fold, f'{image_name}.json')
    if tags_dict is None:
        logger.error(f'download_tags {output_file}')
        return False
    os.makedirs(os.path.join(output_fold), exist_ok=True)
    if os.path.exists(output_file):
        logger.warning(f'download_tags {output_file} exist')
    json = ','.join(tags_dict)
    with open(output_file, "w") as file:
        file.write(json)
    logger.info(f'download_tags end {json}')
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


def validate_sticker(input_path):
    logger.info(f'validate_sticker start {input_path}')
    if os.path.exists(input_path):
        logger.info(f'validate_sticker exist')
    else:
        logger.error(f'validate_sticker not exist')
        return False
    size = os.path.getsize(input_path)
    if size > size_limit:
        logger.error(f'validate_sticker size {input_path} {size} fail')
        return False

    try:
        frames = []
        with Image.open(input_path) as im:
            # Extract all frames from the image
            try:
                while True:
                    frames.append(im.copy())
                    im.seek(len(frames))
            except EOFError:
                pass
            durations = []
            for frame in frames:
                durations.append(frame.info['duration'])
            if len(frames) <= 1:
                logger.error(f'validate_sticker frames {input_path} {frames} fail')
                return False
            total_duration = sum(durations)
            if total_duration > 10000:
                logger.error(f'validate_sticker total_duration {input_path} {total_duration} fail')
                return False
            for duration in durations:
                if duration < 8:
                    logger.error(f'validate_sticker duration {input_path} {duration} fail')
                    return False
            return True

    except Exception as e:
        logger.error(e)
        return False


def validate():
    file_name = '6.webp'
    input_file = os.path.join('sticker', 'actions', 'breaking-up', 'resize', file_name)
    output_folder = os.path.join('sticker', 'actions', 'breaking-up', 'compressed')
    output_file = os.path.join(output_folder, file_name)
    # image_resize(input_file, output_folder, file_name, 512, 512)
    # image_zip(input_file, output_folder, file_name)
    validate_sticker(output_file)


def main():
    download_categories()
    # download_related()
    # validate()





main()
