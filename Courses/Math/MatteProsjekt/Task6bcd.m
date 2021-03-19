function [error_table] = Task6bcd()

L = 2.0;

% 6a
error_table = zeros(11, 3);
c = zeros(11, 1);
h2 = zeros(11,1);

for k=1:11
    n=10*2^k;
    error = (Task2(n)\sinusoidal_force(n)) - sinusoidal_correct(n);

    c(k) = condest(Task2(n)) * eps;
    h2(k) = L^2/(10*2^k);

    % first column - n
    error_table(k, 1) = n;

    % second column - error at position x
    error_table(k, 2) = abs(error(n));

    % third column - condition number
    error_table(k, 3) = condest(Task2(n), 2);

end

% 6b
loglog(error_table(:, 1), error_table(:,2));
hold on

% 6c
%Plot the condition number to A multiplied with machine epsilon
loglog(error_table(:,1), c);

% Theoretical Error h^2
loglog(error_table(:,1), h2);
end

function [B] = sinusoidal_force(n)
L = 2.0;                % Length
h = L / n;              % Step length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
p = 100;                % Pressure

pile = p * g * sin((h:h:2)' * pi/2);
B = h^4/(E*I) * (ones(n, 1) * g * d * w * t + pile);
end

function [correct] = sinusoidal_correct(n)
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
    correct(k,1) = f/24/E/I * x^2 * (x^2 - 8*x + 24) + (p*g*2/(E*I*pi))*( 8/(pi^3)*sin(pi/2*x)-(x^3)/6+(x^2)-4/(pi^2)*x);
end
end
