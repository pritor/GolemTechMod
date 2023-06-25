import os
import cv2
import shutil
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image

Golem_folder_path = "Golem_folder"

Golems_list = os.listdir(Golem_folder_path)

os.mkdir("Body")
os.mkdir("Legs")
os.mkdir("Left_arm")
os.mkdir("Right_arm")
os.mkdir("Head")
os.mkdir("Custom_golem")


def add_folder(part_of_golem):
    for folder in Golems_list:
        shutil.copy2("Golem_folder\\" + folder,
                     part_of_golem + "\\" + part_of_golem + "_" + folder)


def golem_split(part_of_golem, x_coord, y_coord, shift_along_x, shift_along_y):
    add_folder(part_of_golem)
    folder_path = part_of_golem

    arr = os.listdir(folder_path)

    for folder in arr:
        temp = folder_path + "\\" + folder
        img = cv2.imread(temp)
        img = img[x_coord:x_coord + shift_along_x, y_coord:y_coord + shift_along_y]
        cv2.imwrite(temp, img)


def golem_concat():


    red = np.zeros((128, 128))
    green = np.zeros((128, 128))
    blue = np.zeros((128, 128))

    red_img = Image.fromarray(red).convert("L")
    green_img = Image.fromarray(green).convert("L")
    blue_img = Image.fromarray(blue).convert("L")

    custom_golem = Image.merge("RGB", (red_img, green_img, blue_img))

    for body in Golems_list:
        n_body = ""
        n_l_arm = ""
        n_r_arm = ""
        n_legs = ""
        head = body

        temp = "Head\\" + "Head_" + head
        n_body = head[0]
        head = Image.open(temp)

        temp = "Body\\" + "Body_" + body
        body = Image.open(temp)

        for l_arm in Golems_list:
            temp = "Left_arm\\" + "Left_arm_" + l_arm
            n_l_arm = l_arm[0]
            l_arm = Image.open(temp)

            for r_arm in Golems_list:
                temp = "Right_arm\\" + "Right_arm_" + r_arm
                n_r_arm = r_arm[0]
                r_arm = Image.open(temp)

                for legs in Golems_list:
                    temp = "Legs\\" + "Legs_" + legs
                    n_legs = legs[0]
                    legs = Image.open(temp)

                    custom_golem.paste(head, (0, 0))
                    custom_golem.paste(body, (0, 21))
                    custom_golem.paste(r_arm, (59, 21))
                    custom_golem.paste(l_arm, (59, 57))
                    custom_golem.paste(legs, (36, 0))

                    custom_golem.save("Custom_golem\\" + "Custom_golem_" + n_l_arm + n_r_arm + n_body + n_legs + ".png")



Golem_parts = [["Body", 21, 0, 106, 58],
               ["Head", 0, 0, 20, 35],
               ["Right_arm", 21, 59, 35, 68],
               ["Left_arm", 57, 59, 70, 68],
               ["Legs", 0, 36, 20, 91]]

for name, x, y, width, height in Golem_parts:
    golem_split(name, x, y, width, height)

golem_concat()





'''def Left_arm_split():
    Left_arm_folder_path = "Left_arm"

    Left_arm_list = os.listdir(Left_arm_folder_path)

    for folder in Left_arm_list:

        temp = Left_arm_folder_path + "\\" + folder

        img = cv2.imread(temp)

        width, height = 70, 68
        x, y = 57, 59

        img = img[x:x+width, y:y+height]

        cv2.imwrite(temp, img)


def Right_arm_split():
    Right_arm_folder_path = "Right_arm"

    Right_arm_list = os.listdir(Right_arm_folder_path)

    for folder in Right_arm_list:
        temp = Right_arm_folder_path + "\\" + folder

        img = cv2.imread(temp)

        width, height = 35, 68
        x, y = 21, 59

        img = img[x:x + width, y:y + height]

        cv2.imwrite(temp, img)

def Legs_split():
    Legs_folder_path = "Legs"

    Legs_list = os.listdir(Legs_folder_path)

    for folder in Legs_list:
        temp = Legs_folder_path + "\\" + folder

        img = cv2.imread(temp)

        width, height = 20, 91
        x, y = 0, 36

        img = img[x:x + width, y:y + height]

        cv2.imwrite(temp, img)

def Head_split():
    Head_folder_path = "Head"

    Head_list = os.listdir(Head_folder_path)

    for folder in Head_list:
        temp = Head_folder_path + "\\" + folder

        img = cv2.imread(temp)

        width, height = 20, 35
        x, y = 0, 0

        img = img[x:x + width, y:y + height]

        cv2.imwrite(temp, img)

def Body_split():
    Body_folder_path = "Body"

    Body_list = os.listdir(Body_folder_path)

    for folder in Body_list:
        temp = Body_folder_path + "\\" + folder

        img = cv2.imread(temp)

        width, height = 106, 58
        x, y = 21, 0

        img = img[x:x + width, y:y + height]

        cv2.imwrite(temp, img)
'''