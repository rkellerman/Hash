clc, clear, close all


dinfo = dir('./results/*.txt');

figure;
hold on;
xlim([-10 10])
prevB = '-1'
i = 0;

for K = 1 : length(dinfo)
  thisfilename = dinfo(K).name;  %just the name
  data = load(strcat('./results/', thisfilename)); %load just this file
  
  [filepath,name,ext] = fileparts(thisfilename);
  B = name(2);
  
  
  if B ~= prevB
      i = 0;
  end
  
  x = log(data(:,1));
  y = log(data(:,2));
  
  xlim = -10:10;
  
  if B == '1'
      plot(x, y, 'bo')
      if i == 0
        fit = polyfit(x, y, 1)
        plot(xlim, polyval(fit, xlim), 'b-')
      end
  elseif B == '2'
      plot(x, y, 'go')
      if i == 0
        fit = polyfit(x, y, 1);
        plot(xlim, polyval(fit, xlim), 'g-')
      end
  else
      plot(x, y, 'ro')
      if i == 0
        fit = polyfit(x, y, 1);
        plot(xlim, polyval(fit, xlim), 'r-')
      end
  end
  
  i = i + 1;
  
  prevB = B;
  
end

h(1) = plot(NaN,NaN,'ob');
h(2) = plot(NaN,NaN,'og');
h(3) = plot(NaN,NaN,'or');
legend(h, 'B = 1','B = 2','B = 4', 'location', 'nw');
title('Hash test results')
xlabel('Time')
ylabel('Attempts')
