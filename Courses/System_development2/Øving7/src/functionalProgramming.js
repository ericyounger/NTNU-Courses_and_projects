/* Oppgave 1 */

let v1 = [1, 2, 3];
let v2 = [4, 5, 6];


let v1Return = v1.map(val => 2+val);
let v2Return = v2.map(val => 2*val);

console.log("V1 + 2: " + v1Return);
console.log("V2 * 2: " + v2Return);

let v1mean = v1.reduce((num, acc) => num + acc)/v1.length;
console.log("Mean: " + v1mean);

let v1dotv2 = v1.map((val, i) => val*v2[i]).reduce((num, acc)=> num + acc);
console.log("Sum of V1 dot V2: " + v1dotv2);

let sumTwoArrays = v1.reduce((num, acc)=>num+acc) + 2 * v2.reduce((num, acc)=> num+acc);
console.log("Sum of two arrays * second array: "+ sumTwoArrays);

let v1_as_string = v1.map((val)=> val.toString());
console.log(v1_as_string);

/* Oppgave 2 */ 

class Complex {
    real: number;
    imag: number;

    constructor(real: number, img: number) {
        this.real = real;
        this.imag = img;
    }
}

let v = [new Complex(2, 2), new Complex(1, 1)];

let retArray = v.map(val => `${val.real} + ${val.imag}i`);
console.log(retArray);

console.log("magnitude of v elements: [" + v.map((val)=> Math.sqrt(Math.pow(val.real,2)+Math.pow(val.imag,2)))+ " ]");
console.log(v.reduce((val, acc, i)=> `Sum of v: Complex { real: ${val.real+acc.real}, imag: ${val.imag+acc.imag} }`));


//Oppgave 3
let students = [{ name: 'Ola', grade: 'A' }, { name: 'Kari', grade: 'C' }, { name: 'Knut', grade: 'C' }];


console.log("students elements as strings: [ " + students.map(val => `${val.name} got ${val.grade} `) + "]");

console.log("How many got C: " + students.filter(obj => obj.grade == "C").reduce((val, acc, i)=> i+1));
console.log("Percentage of C grades: " + students.filter(val => val.grade == "C").reduce((val, acc, i)=> i+1)/students.length);

console.log("Did anyone get A: " +(students.some(val => (val.grade == "A")) ? "Yes" : "No"));
console.log("Did anyone get F: " + (students.some(val => (val.grade == "F")) ? "Yes" : "No"));