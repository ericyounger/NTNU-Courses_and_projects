function [] = Task4d()
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density
f = g * d * w * t;      % No load on board

% Numeric fourth derivative from 4c
numeric_y = Task4c();
% Exact fourth derivative
exact_y = (f/(E*I));

disp("Forward error:");
fwd_err = abs(exact_y - numeric_y);
disp(abs(fwd_err));

disp("Relative forward error:")
relative_fwd_err = abs((fwd_err)/exact_y);
disp(relative_fwd_err);

disp("Error enlargement")
err_magnified = relative_fwd_err / 2^-52;
disp(err_magnified);

disp("Condition number");
A = Task2(10);
disp(condest(A));
end
