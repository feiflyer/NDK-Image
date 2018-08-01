
cmake_minimum_required(VERSION 3.4.1)

#设置头文件搜索路径（和此txt同个路径的头文件无需设置），可选
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR})


#设置giflib的头文件路径
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/../giflib)

#设置webplib的头文件路径
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/../webplib)

#注意GLOB_RECURSE 和GLOB的区别
file(GLOB_RECURSE framesequenceSource ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)

set(GIF_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../giflib)
set(WEBP_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../webplib)

file(GLOB gifLibSource ${GIF_DIR}/*.c)

#注意GLOB_RECURSE 和GLOB的区别
file(GLOB_RECURSE webpLibSource ${WEBP_DIR}/*.c)

add_library(
             giflib

             SHARED

             ${gifLibSource} )

  add_library(
               webplib

               SHARED

               ${webpLibSource} )


add_library(
             framesequence

             SHARED

             ${framesequenceSource} )


find_library(
              log-lib

              log )


target_link_libraries(
                        framesequence


giflib
webplib

                        #frameSequence还依赖了这个ndk的库
                        jnigraphics

                        ${log-lib} )

