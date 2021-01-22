# Password
This simple program arised from a personal need to find annoying, duplicate characters in passwords generated by my password manager, Bitwarden. From there, I decided to create my own password generator using Java SecureRandom.


### Requirements
A Java compiler is suffice.


### Does it do anything?
Not much for now ...

```
        __Menu Options__:
    1.) Check Duplicate Character
    2.) Password Generator
    3.) Unique Password Generator
    4.) Exit
```


### Discussion
Unique Password Generator is a personal preference and should by no mean be perceived as being more secured.
Password Generator and Unique Password Generator fall under the categories of permutation *with* repetition and permutation *without* repetition respectively.<br><br>
Permutation *with* repetition:
> P = n<sup>r</sup> | n = 94, r > 0<br>
> - n is the pool of characters to choose from in "combined" and r is the length of the password<br>
> - P = n \*...\* n<br>

Permutation *without* repetition:
> P = n! / (n-r)! | n = 94, 0 <= r <= n<br>
> - P = (n) \* (n-1) \* (n-2) \*...\* 1<br>
> - P = n! when r = n<br>

n! < n<sup>n</sup>, with both having the time complexity of O(n<sup>n</sup>).

As a general rule, choose random generated password that has no discernible "word" with length > 8 to mitigate the possibility of dictionary attack and brute force attack respectively.


---


### Tasks
- [ ] Modify option 1 to replace duplicate with unique characters. Assuming most passwords are less than 94 char long.
- [ ] Add SHA encrypt
- [ ] Add SHA decrypt
- [ ] Add AES encrypt
- [ ] Add AES decrypt
- [ ] Test password strength


### TODO
* Add few more features along with my renewed interest in cryptography.
* Learn how to create a docker image for this project.
