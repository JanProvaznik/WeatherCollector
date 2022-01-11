#!/usr/bin/env python3
import numpy as np
from sklearn.linear_model import LinearRegression
import sys
import pandas as pd
import datetime

model = LinearRegression()


def main():
    if len(sys.argv) >= 3:
        csv_path = sys.argv[1]
        train(csv_path)

        timestamps = sys.argv[2:]
        predict(timestamps)
    else:
        print("Not enough arguments")


def train(csv_path):
    dataframe = pd.read_csv(csv_path)

    model.fit(np.array(dataframe.iloc[:, 0].values.tolist(), int).reshape(-1, 1),
              np.array(dataframe.iloc[:, 1].values.tolist(), float).reshape(-1, 1))


def predict(timestamps):
    print("Predictions:")
    for ts in timestamps:
        prediction = model.predict([[int(ts)]])
        print(
            f"Temperature at {datetime.datetime.fromtimestamp(int(ts)).strftime('%Y-%m-%d %H:%M:%S')} ... {prediction[0][0]} degrees Celsius")


if __name__ == "__main__":
    main()
