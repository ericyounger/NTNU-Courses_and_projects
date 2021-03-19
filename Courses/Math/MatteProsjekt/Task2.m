function A = Task2(n)
    %Makes a nxn matric of ones
    e = ones(n, 1); 
    
    %Make a diagnoal matrix
    A = spdiags([e, -4*e, 6*e, -4*e, e], [-2, -1, 0, 1, 2], n, n);
    
    A(1, 1:4) = [16 -9 8/3 -1/4];
    A(n-1, n-3:n) = [16 -60 72 -28] / 17;
    A(n, n-3:n) = [-12 96 -156 72] / 17;
end
 