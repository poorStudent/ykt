# 设置构建本机库所需的最低 CMake 版本。

cmake_minimum_required(VERSION 3.10.2)

# 声明并命名项目。

project("vmsZjy")


# 设置生成的so动态库最后输出的路径，set后面两个参数，
# 前面一个是命令，后面是路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY
        ${PROJECT_SOURCE_DIR}/../libsOut/${ANDROID_ABI})

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY
        ${PROJECT_SOURCE_DIR}/../shellOut/${ANDROID_ABI})

file(GLOB
        jni
        native-lib.cpp MD5.cpp base64.c aes.c)#设置统配符

add_library(cloud-core SHARED IMPORTED)

set_target_properties(cloud-core
        PROPERTIES
        IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../libsOut/${ANDROID_ABI}/libnative-lib.so)

add_library( # 设置库的名称。
        native-lib
        # 将库设置为共享库。
        SHARED
        # 提供源文件的相对路径。
        ${jni}

        )
# 搜索指定的预构建库并将路径存储为变量。由于 CMake 默认在搜索路径中包含系统库，因此您只需指定要添加的公共 NDK 库的名称即可。 CMake 在完成构建之前验证库是否存在。

find_library(
        # 设置路径变量的名称,后面会引用到  log-lib   //log日志,默认都需要添加
        log-lib

        # 指定 NDK 库的名称
        log)


find_library(
        utils-lib

        utils)

# 指定库 CMake 应链接到您的目标库。您可以链接多个库，例如您在此构建脚本中定义的库、预构建的第三方库或系统库。

target_link_libraries( # 指定目标库。
        native-lib

        ${log-lib})


add_executable(nshell checkBuy/native-shell.cpp)

file(GLOB nshell src/main/cpp/checkBuy/native-shell.cpp)#设置统配符
message("--------------------${nshell}")
#[[
#设置头文件搜索路径（和此txt同个路径的头文件无需设置），可选 对应于Android.mk 下的 LOCAL_C_INCLUDES
 include_directories(Cipher
        Core
        Core/Common
        Core/DB
        Core/DG)
 file(GLOB Cipher "Cipher/*.cpp")
]]

#[[
比如添加一个第三方库libpng,这两个需要配合使用
add_library(libpng
STATIC
IMPORTED)
set_target_properties( libpng
PROPERTIES
IMPORTED_LOCATION
......../libpng.a )
使用的时候，只需要修改将两个libpng名称改成你需要的，可以随意取，该名称是后面用来链接使用的，再修改一下对应文件路径就ok。
对应于Android.mk 下的 LOCAL_LDLIBS

target_link_libraries(

 #指定目标library中。
        通过add_library添加的直接写设置的名字，
        一种是SHARED,一般来说就是我们需要生成的so文件
        二中是STATIC IMPORTED 添加的第三方静态库
        demo
        libpng


        //链接本地NDK里面的库
        ${log-lib}
        ${jnigraphics-lib}
        )

        8.如何通过CMakeList.txt生成多个so库？针对不同的so库，编写不同的CMakeList.txt，然后在主CMakeList.txt中加载

# 再主CMakeList.txt中加载其他的子CMakeList.txt文件
# 指定子CMakeList.txt文件路径(指定到CMakeList.txt所在文件夹即可)
ADD_SUBDIRECTORY(FrameWork/ThirdParty/a)
ADD_SUBDIRECTORY(b)
]]

