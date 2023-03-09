import pandas as pd

roasFile = "./cvs/roas.csv"
retentionFile = "./cvs/retention.csv"
revenueFile = "./cvs/revenue.csv"
resultFile = "./cvs/new.csv"


def sort(df, f):
    df.sort_values(by=['Media Source', 'Campaign', 'Cohort Day'], inplace=True, ascending=True)
    df.to_csv(f, index=False)


def format_float(df):
    return round(df, 4)


def percent(df, f):
    # 计算 sessions - Unique users - day 1 列和 Users 列的百分比
    day1 = 'sessions - Unique users - day 1'
    if 'retention2' in df.columns:
        df.drop('retention2', axis=1, inplace=True)

    df['retention2'] = df.apply(lambda row: str(row[day1]) + "(" + str(format_float((row[day1] / row['Users'])) * 100) + "%" + ")", axis=1)

    # 保存修改后的 retention.csv 文件
    df.to_csv(f, index=False)


# 读取 roas.csv 文件
roas_df = pd.read_csv(roasFile)

# 读取 retention.csv 文件
retention_df = pd.read_csv(retentionFile)

percent(retention_df, retentionFile)

# 读取 revenue.csv 文件
revenue_df = pd.read_csv(revenueFile)

# sort(roas_df, roasFile)
# sort(retention_df, retentionFile)
# sort(revenue_df, revenueFile)

# 获取 retention_df 中的 H 列数据
h_column = retention_df['retention']

# 将 H 列数据插入到 roas_df 中的 G 列后面
roas_df.insert(loc=7, column='retention', value=h_column)

# 保存修改后的 roas.csv 文件
roas_df.to_csv(resultFile, index=False)
