function y = Task3(n)

%Make a nxn matrix A. Uses 10 intervalls.
A = Task2(n);  

% No load
z = @(x) (0);  

%Make b matrix. Uses 10 intervalls. 
b = FX(z,zeros(n,1));

%Solve for y
y = A\b;

end
 
    