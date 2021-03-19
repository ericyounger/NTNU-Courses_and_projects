use std::io::{self, BufRead};

fn change_string(x: &str) -> String {
    return x.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
}

fn main() {
    //READ LINE
    let mut line = String::new();
    let stdin = io::stdin();
    stdin.lock().read_line(&mut line).unwrap();

    //REPLACE <, >, &
    let output = change_string(&line);
    println!("{}", output);


    let example = String::from("1 < 2 && 2 > 1");
    //print example 
    let output_example = change_string(&example);
    println!("{}", output_example);

}
