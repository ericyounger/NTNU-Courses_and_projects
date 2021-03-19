function f = FX(s,x)
%Constants
L = 2.0;                % Length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
f = g * d * w * t;      % No load on board

%Returns the size of the dimensions of the arry.
[n, ~] = size(x);
h = L / n;              % Step length

f = ones(n, 1) * f;

% Loop through to get the system of linear equations for the
% displacements
for i=1:n
    f(i) = (f(i) + s(x(i))) * (h^4/(E*I));
end
end