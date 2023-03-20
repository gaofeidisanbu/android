import os
import time
from urllib import request
from urllib.error import URLError

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import ssl

# 设置 Chrome 驱动器的路径
chromedriver_path = '/usr/local/bin/chromedriver'

# 设置 Artist 和作品集的 Xpath
artist_xpath = '//a[starts-with(@class, "Wrapper-sc-")]'
artist_name_xpath = '//div[starts-with(@class, "HeaderSC-sc-")]'
artist_gallery_xpath = '//a[starts-with(@class, "LinkContainer-sc-")]'

# 设置下载图片的最大数量
max_images_per_gallery = 10

# 设置下载图片的最大重试次数
max_download_retries = 3

# 设置下载图片时的超时时间（秒）
download_timeout = 30

# 启动 Chrome 浏览器
options = webdriver.ChromeOptions()
options.add_argument('--ignore-certificate-errors')
options.add_argument('--incognito')
options.add_argument('--headless')
# 设置 ChromeDriver 路径
chromedriver_path = '/usr/local/bin/chromedriver'

# 创建 ChromeDriver 服务
service = Service(executable_path=chromedriver_path)

# 创建 ChromeDriver 实例
driver = webdriver.Chrome(service=service, options=options)

# 访问网页
driver.get('https://giphy.com/artists')

# 等待 Artist 加载完成
WebDriverWait(driver, 100).until(EC.presence_of_all_elements_located((By.XPATH, artist_xpath)))
print("WebDriverWait 1")

# 获取所有 Artist 的链接
artist_links = driver.find_elements(By.XPATH, artist_xpath)

# 对于每个 Artist
for i, artist_link in enumerate(artist_links):
    print(f'Processing Artist {i + 1}/{len(artist_links)}')

    # 点击链接进入 Artist 的页面
    artist_link.click()

    # 等待 Artist 页面加载完成
    WebDriverWait(driver, 100).until(EC.presence_of_all_elements_located((By.XPATH, artist_name_xpath)))
    print("WebDriverWait 2")
    # 获取 Artist 的名字，并创建一个文件夹
    artist_name = driver.find_element(By.XPATH, '//h1/span').text
    print("artist_name="+artist_name)
    artist_folder = f'./{artist_name}'
    os.makedirs(artist_folder, exist_ok=True)

    # 获取所有作品集的链接
    gallery_links = driver.find_elements(By.XPATH, artist_gallery_xpath)

    # 对于每个作品集
    for j, gallery_link in enumerate(gallery_links):
        print(f'  Processing Gallery {j + 1}/{len(gallery_links)}')
        # 获取作品集的名字，并创建一个文件夹
        gallery_name = driver.find_element(By.XPATH, '//a/h2').text
        gallery_folder = f'{artist_folder}/{gallery_name}'
        os.makedirs(gallery_folder, exist_ok=True)
        print("gallery_name="+gallery_name)
        # 点击链接进入作品集的页面
        gallery_link.click()
        image_xpath='//img[@class="giphy-gif-img giphy-img-loaded"]'
        # 等待作品集页面加载完成
        WebDriverWait(driver, 100).until(
            EC.presence_of_all_elements_located((By.XPATH, image_xpath)))
        image_links = driver.find_elements(By.XPATH, image_xpath)
        num_images = min(max_images_per_gallery, len(image_links))
        for k in range(num_images):
            image_link = image_links[k]
            image_url = image_link.get_attribute('src')

            # 获取图片名字
            image_name = image_url.split('/')[-1]

            # 下载图片
            for retry in range(max_download_retries):
                try:
                    with request.urlopen(image_url, timeout=download_timeout) as response, open(
                            f'{gallery_folder}/{image_name}', 'wb') as f:
                        f.write(response.read())
                    break
                except URLError as e:
                    print('reason', e.reason)
                    print(f'Download failed for {image_url}. Retry {retry + 1}/{max_download_retries}')
                    time.sleep(1)

        # 返回到 Artist 页面
        driver.back()

        # 等待 Artist 页面加载完成
        WebDriverWait(driver, 100).until(EC.presence_of_all_elements_located((By.XPATH, image_xpath)))

    # 返回到 Artist 页面
    driver.back()

    # 等待 Artist 页面加载完成
    WebDriverWait(driver, 100).until(EC.presence_of_all_elements_located((By.XPATH, artist_gallery_xpath)))





