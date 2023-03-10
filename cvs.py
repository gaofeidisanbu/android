import datetime

import pandas as pd
import numpy as np

from datetime import datetime, timedelta

roasFile = "./cvs/roas.csv"
retentionFile = "./cvs/retention.csv"
revenueFile = "./cvs/revenue.csv"
clearBeforeFile = "./cvs/clear_before.csv"
clearAfterFile = "./cvs/clear_after.csv"

retentionDay1User = 'sessions - Unique users - day 1 - partial'
roasRetentionDay1 = "retention day1 "
roasRetentionDay1Percent = "retention day1 percent"

# 读取 roas.csv 文件
roas_df = pd.read_csv(roasFile)

# 读取 retention.csv 文件
retention_df = pd.read_csv(retentionFile)

# 读取 revenue.csv 文件
revenue_df = pd.read_csv(revenueFile)


def sort(df, f):
    df.sort_values(by=['Media Source', 'Campaign', 'Cohort Day'], inplace=True, ascending=True)
    df.to_csv(f, index=False)


def format_float(df):
    value = round(df * 100, 2)
    return value


def calculate_row(df_row):
    return str((format_float(df_row[retentionDay1User] / df_row['Users']))) + "%"


# 计算day0留存率
def retention_day0_percent(df, f):
    # 计算 sessions - Unique users - day 1 列和 Users 列的百分比

    drop_column(df, roasRetentionDay1Percent)
    df[roasRetentionDay1Percent] = df.apply(lambda df_row: calculate_row(df_row), axis=1)


def clear_table(df, f):
    # 获取当前日期
    curr_day_str = datetime.now().strftime('%Y/%-m/%-d')
    curr_day = datetime.now()
    # 遍历每一行
    for index, row in df.iterrows():
        # 获取该行的Cohort Day
        n_day_str = row['Cohort Day']
        n_day = datetime.strptime(n_day_str, '%Y-%m-%d')
        # print("start "+n_day_str)

        # 循环30次
        for i in range(30):
            # 获取临时字符串变量temp
            temp = f(i)

            # 获取日期
            column_day = n_day + timedelta(days=i)
            column_day_str = column_day.strftime('%Y-%m-%d')
            print("column_day_str " + column_day_str + " curr_day " + curr_day_str)

            # 如果 column_day 大于 curr_day，且该行的 temp 列存在，则清空该行的 temp 列
            if column_day > curr_day and temp in df.columns:
                df.at[index, temp] = np.nan
                print("clear column_day_str " + column_day_str)


# 删除df的f列
def drop_column(df, f):
    if f in df.columns:
        df.drop(f, axis=1, inplace=True)


# 获取roas表格day n列名字
def get_roas_column(day_str):
    return f"roas - Rate - day {day_str} - partial"


# 获取revenue表格day n名字
def get_revenue_column(day_str):
    return f"revenue - sum - day {day_str} - partial"


def sort_all_table():
    sort(roas_df, roasFile)
    sort(retention_df, retentionFile)
    sort(revenue_df, revenueFile)


# retention表中day0用户数和留存率添加到roas
def merge_retention_to_roas():
    # 计算day0留存率
    retention_day0_percent(retention_df, retentionFile)
    # 获取day0用户数
    retention_day0_user_column = retention_df[retentionDay1User]

    # 获取day0用户留存率
    retention_day0_user_percent_column = retention_df[roasRetentionDay1Percent]

    drop_column(roas_df, roasRetentionDay1)
    roas_df.insert(loc=7, column=roasRetentionDay1, value=retention_day0_user_column)

    drop_column(roas_df, roasRetentionDay1Percent)
    roas_df.insert(loc=8, column=roasRetentionDay1Percent, value=retention_day0_user_percent_column)


# roas添加day n收入
def merge_revenue_to_roas():
    # 提取包含关键字的列
    revenue_cols = [col for col in revenue_df.columns if 'revenue - sum - day' in col.lower()]

    # 将提取的列插入到 roas_df 中
    for col in revenue_cols:
        roas_df[col] = revenue_df[col]


def clear_roas():
    roas_df.to_csv(clearBeforeFile, index=False)
    clear_table(roas_df, get_roas_column)
    clear_table(roas_df, get_revenue_column)
    roas_df.to_csv(clearAfterFile, index=False)


sort_all_table()
merge_retention_to_roas()
merge_revenue_to_roas()
clear_roas()















