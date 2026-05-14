# Android Security Demo Project

Dự án này là một tập hợp các ứng dụng Android minh họa các khái niệm về thành phần cơ bản của Android (Component) và cách chúng tương tác hoặc chia sẻ dữ liệu.

## Cấu trúc Project

Project bao gồm 3 module chính:

### 1. **App (`:app`)**
- Ứng dụng chính đóng vai trò là giao diện điều khiển hoặc ứng dụng khách để thử nghiệm.
- Sử dụng Jetpack Compose cho giao diện người dùng.

### 2. **Audit Logger (`:auditlogger`)**
- **Chức năng:** Ghi lại lịch sử các hoạt động (logs).
- **Thành phần chính:**
    - `AuditLogReceiver`: Một BroadcastReceiver lắng nghe action `com.example.auditlogger.action.WRITE_LOG`.
    - `LogStorage`: Quản lý lưu trữ log dưới dạng JSON.
- **Cấu hình ký ứng dụng:** Đã cấu hình Signing Config sử dụng keystore `auditlogger.jks`.

### 3. **Secret Notes (`:secretnotes`)**
- **Chức năng:** Quản lý ghi chú cá nhân.
- **Thành phần chính:**
    - `SecretNoteProvider`: Một Content Provider cho phép các ứng dụng khác (có quyền) truy cập và truy vấn danh sách ghi chú.
    - `NoteStorage`: Quản lý lưu trữ ghi chú.

## Cách sử dụng

### Gửi Log tới Audit Logger
Bạn có thể gửi log từ các ứng dụng khác hoặc qua ADB bằng lệnh:
```bash
adb shell am broadcast -a com.example.auditlogger.action.WRITE_LOG --es message "Noi dung log o day"
```

### Truy cập ghi chú từ Secret Notes
Các ứng dụng khác có thể truy vấn dữ liệu từ `SecretNoteProvider` thông qua URI:
`content://com.example.secretnotes.provider/notes`

## Cấu hình Build & Chạy
1. Mở dự án bằng **Android Studio**.
2. Thực hiện **Gradle Sync**.
3. Chọn module muốn chạy (`app`, `auditlogger`, hoặc `secretnotes`) trên thanh công cụ và nhấn **Run**.

## Lưu ý bảo mật (Dành cho Demo)
- File Keystore `auditlogger.jks` và mật khẩu hiện đang được để trực tiếp trong mã nguồn cho mục đích demo/bài tập.
- **Cảnh báo:** Trong các dự án thực tế, tuyệt đối không push file `.jks` và thông tin mật khẩu lên các kho lưu trữ công khai như GitHub.
