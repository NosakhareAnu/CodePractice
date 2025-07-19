# course = "python programming"
# print(course.upper())
# print(course.lower())
# print(course.title())
# print(course.rstrip())
# print(course.find(" "))
# print(course.replace("p", "j"))
# print("pro" in course)
# print("lam" not in course)


# x = input("x: ")


# x = input("x: ")
# y = int(x) + 1.5
# print(f"{x},{y}")


# age = int(input("how old are you: "))
# message = "nice" if age >= 18 else "bad"
# print(message)

# 12

# if age > 18:
#     message = "nice"
# else:
#     message = "bad"

# print(message)

# if age >= 10:
#     print("good")
# else:
#     print("bad")


# income = float(input("What is your income: "))

# high_income = income >= 6.5
# low_income = income <= 6.5

# if high_income:
#     print("high income")
# if low_income:
#     print("low income ")


# import math
# import string

# num = math.sqrt(49)
# print(f"Square root: {num}")
# print(f"Rounded value: {round(num, 1)}")
# print(f"Letters: {string.ascii_letters}")


# import math
# num =round(math.sqrt(float((input("number: ")))))
# print(num)

import tkinter as tk

def click_button(value):
    current = str(entry.get())
    entry.delete(0, tk.END)
    entry.insert(0, current + value)

def clear():
    entry.delete(0, tk.END)

def calculate():
    try:
        result = eval(entry.get())
        entry.delete(0, tk.END)
        entry.insert(0, str(result))
    except:
        entry.delete(0, tk.END)
        entry.insert(0, "Error")

root = tk.Tk()
root.title("Calculator")

entry = tk.Entry(root, width=30, font=("Arial", 16))
entry.grid(row=0, column=0, columnspan=4)

buttons = [
    ("7",1,0), ("8",1,1), ("9",1,2), ("/",1,3),
    ("4",2,0), ("5",2,1), ("6",2,2), ("*",2,3),
    ("1",3,0), ("2",3,1), ("3",3,2), ("-",3,3),
    ("0",4,0), (".",4,1), ("=",4,2), ("+",4,3),
]

for (text, row, col) in buttons:
    action = lambda val=text: click_button(val) if val != "=" else calculate()
    tk.Button(root, text=text, width=5, height=2, command=action).grid(row=row, column=col)

tk.Button(root, text="C", width=22, height=2, command=clear).grid(row=5, column=0, columnspan=4)

root.mainloop()
