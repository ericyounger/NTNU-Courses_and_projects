function [error_table] = Task5()

error_table = zeros(11, 3);

for k=1:11
    n=10*2^k;
    error = (Task2(n)\simple_force(n)) - simple_correct(n);
    
    % first column - n
    error_table(k, 1) = n;
    
    % second column - error at position x
    error_table(k, 2) = abs(error(n));
    
    % third column - condition number
    error_table(k, 3) = condest(Task2(n), 2);
end
end

function [B] = simple_force(n)
L = 2.0;                % Length
h = L / n;              % Step length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
p = 100;                % Pressure

B = ones(n, 1) * h^4/(E*I) * g * d * w * t;
end

function [correct] = simple_correct(n)
L = 2.0;                % Length
h = L / n;              % Step length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
p = 100;                % Pressure
f = g * d * w * t;      % No load on board

correct = zeros(n, 1);

for k=1:n
    x = h * k;
    correct(k,1) = f/24/E/I * x^2 * (x^2 - 8*x + 24);
end
end
