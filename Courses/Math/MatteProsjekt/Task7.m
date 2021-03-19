function [] = Task7()

n = 1280;               % optimal n from last task
L = 2.0;                % Length
h = L / n;              % Step length

% Make our diver
diver1 = Task2(n)\diverforces(50, 0.3, n);
% Comparison diver to test if heavier diver displace more
diver2 = Task2(n)\diverforces(80, 0.3, n);

disp('Diver 50kg - Deflection of beam in meters: ');
disp(diver1(n));

disp('Diver 80kg - Deflection of beam in meters: ');
disp(diver2(n));

xaxis = h:h:L;

figure
plot(xaxis,diver1,'b',xaxis,diver2,'g');
xlabel('x (meters)');
ylabel('y (meters)');


end

function [B] = diverforces(diverweight,diverwidth, n)
L = 2.0;                % Length
h = L / n;              % Step length
w = 0.30;               % Width
t = 0.03;               % Thickness
I = (w * t^3)/12;       % Area moment of inertia
E = 1.3e10;             % Young's modulus
g = -9.81;              % Gravity constant
d = 480;                % Density

diver = (h:h:L >= L-diverwidth) * g * diverweight / diverwidth;
B = h^4/(E*I) * (ones(n, 1) * g * d * w * t + diver');
end