
cmake_minimum_required(VERSION 3.4.1)

add_library(
             native-lib

             SHARED

             src/main/cpp/native-lib.cpp )

find_library(
              log-lib

              log )


target_link_libraries(
                       native-lib

                       ${log-lib} )


#添加子目录,将会调用子目录中的CMakeLists.txt
ADD_SUBDIRECTORY(src/main/cpp/giflib)
ADD_SUBDIRECTORY(src/main/cpp/webplib)

