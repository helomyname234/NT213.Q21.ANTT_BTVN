# Android Security Demo Project

Dự án này là một tập hợp các ứng dụng Android minh họa các khái niệm về thành phần cơ bản của Android (Component) và cách chúng tương tác hoặc chia sẻ dữ liệu.

## Cấu trúc Project

Project bao gồm 

### 1. **Audit Logger (`:auditlogger`)**
- **Chức năng:** Ghi lại lịch sử các hoạt động (logs).
- **Thành phần chính:**
    - `AuditLogReceiver`: Một BroadcastReceiver lắng nghe action `com.example.auditlogger.action.WRITE_LOG`.
    - `LogStorage`: Quản lý lưu trữ log dưới dạng JSON.
- **Cấu hình ký ứng dụng:** Đã cấu hình Signing Config sử dụng keystore `auditlogger.jks`.

### 2. **Secret Notes (`:secretnotes`)**
- **Chức năng:** Quản lý ghi chú cá nhân.
- **Thành phần chính:**
    - `SecretNoteProvider`: Một Content Provider cho phép các ứng dụng khác (có quyền) truy cập và truy vấn danh sách ghi chú.
    - `NoteStorage`: Quản lý lưu trữ ghi chú.

## Cấu hình Build & Chạy
1. Mở dự án bằng **Android Studio**.
2. Thực hiện **Gradle Sync**.
3. Chọn module muốn chạy (`app`, `auditlogger`, hoặc `secretnotes`) trên thanh công cụ và nhấn **Run**.

