for (let i = 1; i <= 100; i++) {
    if (i % 3 == 0 && i % 5 == 0) {
        console.log("Georgia Tech");
    } else if (i % 3 == 0) {
        console.log("Georgia");
    } else if (i % 5 == 0) {
        console.log("Tech");
    } else {
        console.log(i);
    }
}
let str = "";
function grid(n) {
    for (let i = 0; i < n; i++) {

        if (i % 2 == 0) {
            for (let i = 0; i < n; i ++) {
               str += "8 "; 
            }
            str += "\n";
    
        } else if (i % 2 == 1) {
            str+= " ";
            for (let i = 0; i < n; i++) {
                str += "8 ";
            }
            str += "\n";
        }
    }
    str = str.substring(0, str.length - 1);
    console.log(str);
}

