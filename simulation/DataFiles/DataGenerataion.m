clear
clc

% Benign Data Parameters
avgB = 75;
sigB = 25;

%Malicious Data Parameters
avgM = 125;
sigM = 25;


MalFrequency = 0.3; %Frequency of malicious data
test = 6;           %Variable to keep track of which set of data, used for file naming

test = num2str(test);
type = 0;           %If malicious or normal, normal = 0, malicious = 1


parameter_name = strcat('Test_',test,'_parameters.txt');
Data_name = strcat('Test_',test,'.txt');
Key_name = strcat('Key_Test_',test,'.txt');


Param = fopen(parameter_name,'w');
D = fopen(Data_name,'w');
Key = fopen(Key_name,'w');

for i = 1:1000
    
   if(rand <= MalFrequency)
       datapoint = round(avgM + sigM*randn());
       type = 1;
   else
       datapoint = round(avgB + sigB*randn());
       type = 0;
   end
   
   switch (datapoint)
       case {datapoint < 0}
           datapoint = 0;
       case {datapoint > 255}
           datapoint = 255;
   end 
   
   fprintf(D, '%d\n', datapoint);
   fprintf(Key, '%d\n', type);
   
end

fprintf(Param,' avgMal = %d\n muMal = %d\n avgBenign = %d\n muBenign = %d\n Anomaly Freq = %S1.2\n',avgM,sigM,avgB,sigB,MalFrequency);

fclose('all');