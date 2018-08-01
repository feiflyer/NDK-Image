
cmake_minimum_required(VERSION 3.4.1)

#设置头文件搜索路径（和此txt同个路径的头文件无需设置），可选
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR})

#注意GLOB_RECURSE 和GLOB的区别
file(GLOB_RECURSE webpLibSource ${CMAKE_CURRENT_SOURCE_DIR}/*.c)

add_library(
             webplib

             SHARED

             ${webpLibSource} )



