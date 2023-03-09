import pandas as pd

roasFile = "./cvs/roas.csv"
retentionFile = "./cvs/retention.csv"
revenueFile = "./cvs/revenue.csv"
resultFile = "./cvs/new.csv"

day1User = 'sessions - Unique users - day 1'
retentionDay1 = "retention day2"


def sort(df, f):
    df.sort_values(by=['Media Source', 'Campaign', 'Cohort Day'], inplace=True, ascending=True)
    df.to_csv(f, index=False)


def format_float(df):
    value = round(df * 100, 2)
    return value


def calculate_row(row):
    return str(row[day1User]) + "(" + str((format_float(row[day1User] / row['Users']))) + "%" + ")"


def percent(df, f):
    # 计算 sessions - Unique users - day 1 列和 Users 列的百分比

    if retentionDay1 in df.columns:
        df.drop(retentionDay1, axis=1, inplace=True)

    df[retentionDay1] = df.apply(lambda row: calculate_row(row), axis=1)


# 读取 roas.csv 文件
roas_df = pd.read_csv(roasFile)

# 读取 retention.csv 文件
retention_df = pd.read_csv(retentionFile)

# 读取 revenue.csv 文件
revenue_df = pd.read_csv(revenueFile)

percent(retention_df, retentionFile)

# sort(roas_df, roasFile)
# sort(retention_df, retentionFile)
# sort(revenue_df, revenueFile)

# 获取 retention_df 中的 H 列数据
h_column = retention_df[retentionDay1]

if retentionDay1 in roas_df.columns:
    roas_df.drop(retentionDay1, axis=1, inplace=True)

# 将 H 列数据插入到 roas_df 中的 G 列后面
roas_df.insert(loc=7, column=retentionDay1, value=h_column)

# 保存修改后的 roas.csv 文件
roas_df.to_csv(resultFile, index=False)
