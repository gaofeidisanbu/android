import pandas as pd

roasFile = "./cvs/roas.csv"
retentionFile = "./cvs/retention.csv"
revenueFile = "./cvs/revenue.csv"
resultFile = "./cvs/new.csv"

retentionDay1User = 'sessions - Unique users - day 1 - partial'
roasRetentionDay1 = "retention day1 "
roasRetentionDay1Percent = "retention day1 percent"


def sort(df, f):
    df.sort_values(by=['Media Source', 'Campaign', 'Cohort Day'], inplace=True, ascending=True)


def format_float(df):
    value = round(df * 100, 2)
    return value


def calculate_row(row):
    return str((format_float(row[retentionDay1User] / row['Users']))) + "%"


def retention_percent(df, f):
    # 计算 sessions - Unique users - day 1 列和 Users 列的百分比

    drop_column(df, roasRetentionDay1Percent)
    df[roasRetentionDay1Percent] = df.apply(lambda row: calculate_row(row), axis=1)


def drop_column(df, f):
    if f in df.columns:
        df.drop(f, axis=1, inplace=True)


# 读取 roas.csv 文件
roas_df = pd.read_csv(roasFile)

# 读取 retention.csv 文件
retention_df = pd.read_csv(retentionFile)

# 读取 revenue.csv 文件
revenue_df = pd.read_csv(revenueFile)

retention_percent(retention_df, retentionFile)

sort(roas_df, roasFile)
sort(retention_df, retentionFile)
sort(revenue_df, revenueFile)

# 获取 retention_df 中的 H 列数据
retentionDay1User_column = retention_df[retentionDay1User]

# 获取 retention_df 中的 H 列数据
retentionDay1User_percent_column = retention_df[roasRetentionDay1Percent]

drop_column(roas_df, roasRetentionDay1)
roas_df.insert(loc=7, column=roasRetentionDay1, value=retentionDay1User_column)

drop_column(roas_df, roasRetentionDay1Percent)
roas_df.insert(loc=8, column=roasRetentionDay1Percent, value=retentionDay1User_percent_column)


# 提取包含关键字的列
revenue_cols = [col for col in revenue_df.columns if 'revenue - sum - day' in col.lower()]

# 将提取的列插入到 roas_df 中
for col in revenue_cols:
    roas_df[col] = revenue_df[col]

# 保存修改后的 roas.csv 文件
roas_df.to_csv(resultFile, index=False)






