@echo off
setlocal enabledelayedexpansion

rem 设置要搜索的目标目录
set "target_directory=C:\path\to\your\directory"

rem 遍历目标目录中的所有子目录
for /r "%target_directory%" %%d in (.) do (
    if exist "%%d\Thumb" (
        echo Deleting: "%%d\Thumb"
        rmdir /s /q "%%d\Thumb"
    )
)

endlocal