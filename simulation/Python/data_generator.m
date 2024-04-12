% Sample data
N = 200; % data points

% Benign users
ub = 10;    % Mean for benign users
sigmaB = 1; % Standard deviation for benign users

% Malicious users
um = 20;    % Mean for malicious users 
sigmaM = 0.5; % Standard deviation for malicious users

% Creates benign and malicious sequence of data points 
benign = sigmaB * randn(1,N) + ub;
mal = sigmaM * randn(1,N) + um;

% Split benign and malicious data into two halves
ben1 = benign(1:N/2);
ben2 = benign(N/2+1:end);
mal1 = mal(1:N/2);
mal2 = mal(N/2+1:end);


% Combine vectors
combined = [ben1, mal1, ben2, mal2];

% Open the file for appending
fid = fopen('output1.csv', 'w');

% Print title
fprintf(fid, 'Util\n');

% Write to file
for r = 1:numel(combined)
    fprintf(fid, '%.2f\n', combined(r));
end

% Close the file
fclose(fid);
