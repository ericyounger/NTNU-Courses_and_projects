function y4 = Task4c()
n = 10;                 % Steps
L = 2.0;                % Length
h = L / n;              % Step length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
f = g * d * w * t;      % No load on board
y = zeros(10, 1);

i = 1;
for x = h:h:L
    y(i) = (f / (24 * E * I)) * x^2 * (x^2 - 4 * L * x + 6 * L * L);
    i = i + 1;
end
disp('Y Vektor: ');
disp(y);
A = Task2(10);
disp('Numeric fourth derivative: ');
format long;
y4 = 1/(h^4)*A*y;
disp(y4);
end