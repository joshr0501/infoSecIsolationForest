import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.ensemble import IsolationForest
############## MORE OR LESS FINSIHED

# PLEASE NOTE:
# For the sake of my sanity in terms of creating a ground truth table to check for accuracy
# Benign data points are in the    1-25  & 50-75  percentile range of data points
# Malicious data points are in the 25-50 & 75-100 percentile range of data points
# Mixing the data together within the vetcor would not change the effectiveness of the algorithm
# But I would have to create another function to determine if a point was BENIGN or MAL to begin with in order to check for accuracy of the algorithm & the remaining time does not allow that

# Load data from CSV file
df = pd.read_csv('output1.csv')

# Sample data amount
N = len(df)

# Creates labels to train isolation forest model
num_benign = int(round(N/4,0))
num_malicious = int(round(N/4,0))
mal2 = int(round(N/4,0))
ben2 = int(round(N/4,0))
labels = np.concatenate((np.zeros(num_benign), np.ones(mal2),  np.zeros(ben2), np.ones(num_malicious)))

# reshapes data values into proper format
X = df['Util'].values.reshape(-1, 1)

# Define and fit the Isolation Forest model
model = IsolationForest(contamination = 0.5) 
model.fit(X, y=0, sample_weight = labels)

# Obtain the anomaly scores
anomaly_scores = model.decision_function(X)

# Anomaly scores are typically negative, so you might want to take the absolute value 
anomaly_scores = np.abs(anomaly_scores)

# Finds highest anomaly score to base other anomaly scores off of for evaluation metrics
highest = (max(anomaly_scores))

# Initialize variables
true_positive = 0
false_positive = 0
true_negative = 0
false_negative = 0

# Counts iterations of evaluation metrics based on anomaly score percentage as well as percentile position 
for i in range(len(labels)):
    if ((((anomaly_scores[i] < highest*.5)) and ((i >= (N-N) and i < (N*.25)) or (i>=(N*.50) and i <= (N*.75))))):
        true_negative += 1
    elif ((((anomaly_scores[i] < highest*.5)) and ((i >= N*.25 and i <= N*.49) or ( i> N*.74 and i <= N*.99)))):
        false_negative += 1    
    elif (((anomaly_scores[i] >= highest*.5) and ((i >= N*.25 and i <= N*.49) or ( i> N*.74 and i <= N*.99)) )):
        true_positive += 1
    elif (((anomaly_scores[i] >= highest*.5) and ((i >= (N-N) and i < (N*.25)) or (i>=(N*.50) and i <= (N*.75))) )):
        false_positive += 1    
    i += 1

# Calculates evaluation metrics
tp = round((true_positive)/(N/2),2)
fn = round(1-tp,2)
tn = round((true_negative)/(N/2),2)
fp = round(1-tn,2)

# Prints
print('True Negatives found:', true_negative)
print('True Positives found:', true_positive)
print('False Negatives found:', false_negative)
print('False positives found:', false_positive)

print('True Negative Percentage:', (tn*100) ,'%')
print('True Positive Percentage:', (tp*100) ,'%')
print('False Negative Percentage:', (fn*100) ,'%')
print('False Positive Percentage:', (fp*100) ,'%')

# Plot the anomaly scores
plt.figure(figsize=(10, 6))
plt.scatter(np.arange(len(X)), X, c=anomaly_scores, cmap='YlOrRd')
plt.colorbar(label='Anomaly Score')
plt.title('Anomaly Scores Based on Website Traffic')
plt.xlabel('Hours')
plt.ylabel('Website Traffic')
plt.show()

