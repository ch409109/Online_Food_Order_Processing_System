B1: pull code về
B2: Chạy thử, nếu bị lỗi thì xóa thư mục .gradle đi -> tắt Android Studio -> Vào lại
B3: check trong Android -> gradle-wrapper.properties xem đã đúng phiên bản
8.9 chưa, nếu phiên bản khác thì đổi lại thành 8.9
B4: mở terminal ở góc bên trái dưới, chạy lệnh "gradlew.bat build"
B5: kiểm tra ở file build.gradle (Module app), check compileSdk có phải 35 k, nếu không phải thì đổi thành 35
B6: chạy project

Hello