# Phân tích SOLID và Design Pattern trong Module User

## Tổng quan về Module User

Module User trong hospital-management-system chịu trách nhiệm quản lý người dùng, xác thực và phân quyền. Các thành phần chính bao gồm:

- Entity: User, BlacklistedToken, PatientProfile, DoctorProfile, Role, Permission
- Repository: UserRepository, BlacklistedTokenRepository, PatientProfileRepository, DoctorProfileRepository
- Service: UserService, AuthenticationService, TokenService, JwtService, UserDomainService
- Service Interface: ITokenService, IJwtService, UserDomainService
- Controller: UserController, AuthenticationController
- Mapper: ProfileMapper, UserMapper, DoctorProfileMapper, PatientProfileMapper
- Factory: ProfileFactory, PatientProfileFactory, DoctorProfileFactory
- Exception: UserNotFoundException, AuthenticationException
- Config: SecurityConfig, ApplicationConfig

## Phân tích SOLID

### 1. Single Responsibility Principle (SRP) - Nguyên tắc trách nhiệm đơn

**Phân tích:**

1. **Phân tách Service và Domain Service**
   - `UserService`: Xử lý các business logic liên quan đến User từ góc nhìn ứng dụng
   - `UserDomainService`: Xử lý các domain logic thuần túy của User (tạo, xác thực)
   - `AuthenticationService`: Xử lý logic xác thực và token
   - `TokenService`: Xử lý quản lý token một cách độc lập
   - `LogoutService`: Xử lý logic đăng xuất và vô hiệu hóa token

   **Lợi ích**: Mỗi service chỉ đảm nhiệm một loại chức năng, giúp code dễ bảo trì và mở rộng.
   
   **Nếu không áp dụng**: Logic nghiệp vụ và domain logic sẽ bị trộn lẫn, các service sẽ phình to và khó bảo trì.

2. **Tách biệt Entity và DTO**
   - `User`, `DoctorProfile`, `PatientProfile`: Đại diện cho dữ liệu trong DB
   - `UserDto`, `AuthResponse`, `RegisterRequest`: Đại diện cho dữ liệu trao đổi với client
   
   **Lợi ích**: Bảo vệ dữ liệu entity, chỉ hiển thị những thông tin cần thiết cho client.
   
   **Nếu không áp dụng**: Lộ dữ liệu nhạy cảm, khó kiểm soát dữ liệu in/out.

3. **Tách Repository cho từng Entity**
   - `UserRepository`, `BlacklistedTokenRepository`, `PatientProfileRepository`, `DoctorProfileRepository`
   
   **Lợi ích**: Mỗi repository chỉ chịu trách nhiệm thao tác với một loại entity.
   
   **Nếu không áp dụng**: Repository phình to, khó bảo trì, vi phạm SRP.

4. **Tách Mapper riêng**
   - `UserMapper`, `ProfileMapper`, `DoctorProfileMapper`, `PatientProfileMapper`
   
   **Lợi ích**: Tách riêng logic mapping giữa entity và DTO khỏi service, giúp giảm trách nhiệm của service.
   
   **Nếu không áp dụng**: Logic mapping nằm trong service, làm service phình to và khó bảo trì.

**Cải thiện SRP:**
- `AuthenticationService` đã được cải thiện bằng cách sử dụng `UserMapper` thay vì phương thức `mapToUserDto()` nội bộ.
- Logic quản lý token đã được tách thành `TokenService` riêng biệt.
- `LogoutService` cũng đã được cải thiện để sử dụng `TokenService` thay vì phải tự quản lý logic blacklist.

### 2. Open/Closed Principle (OCP) - Nguyên tắc đóng/mở

**Phân tích:**

1. **Sử dụng Interface cho các Service**
   - `UserDomainService` (interface) và `UserDomainServiceImpl` (implementation)
   - `IJwtService` (interface) và `JwtService` (implementation)
   - `ITokenService` (interface) và `TokenService` (implementation)
   
   **Lợi ích**: Dễ dàng mở rộng bằng cách tạo implementation mới mà không thay đổi code sử dụng interface.
   
   **Nếu không áp dụng**: Phải sửa đổi code hiện tại khi thay đổi implementation, rủi ro gây lỗi.

2. **Strategy Pattern cho Mapper**
   - `ProfileMapper` (interface) và các implementation cho từng role
   
   **Lợi ích**: Dễ dàng thêm mapper mới cho loại user mới mà không cần sửa code cũ.
   
   **Nếu không áp dụng**: Mỗi khi thêm role mới phải sửa đổi UserMapper, vi phạm OCP.

3. **Entity User với Role**
   - Sử dụng enum `Role` để phân loại user
   
   **Lợi ích**: Dễ dàng thêm role mới bằng cách thêm giá trị vào enum.
   
   **Nếu không áp dụng**: Phải sửa đổi nhiều nơi trong code khi thêm role mới.

4. **Token Management Strategy**
   - Chuyển từ quản lý token trực tiếp trong LogoutService sang sử dụng TokenService với blacklist
   
   **Lợi ích**: Dễ dàng thay đổi chiến lược quản lý token (stateful sang stateless) mà không ảnh hưởng đến logic logout.
   
   **Nếu không áp dụng**: Phải sửa nhiều nơi khi thay đổi cách quản lý token.

**Cải thiện OCP:**
- Đã áp dụng Factory Pattern thông qua `ProfileFactory` để tạo profile theo role, thay thế logic if-else trong `AuthenticationService.register()`.
- Việc thêm role mới giờ chỉ cần tạo thêm một implementation mới của `ProfileFactory`.
- Đã chuyển từ stateful JWT sang stateless JWT với blacklist, giúp việc quản lý token linh hoạt hơn và dễ dàng mở rộng.
- LogoutService giờ đã sử dụng TokenService thay vì trực tiếp gọi TokenRepository, tuân thủ OCP tốt hơn.
- Tạo mới các interfaces cho JwtService và TokenService giúp dễ dàng thay đổi implementation mà không cần thay đổi client code.

### 3. Liskov Substitution Principle (LSP) - Nguyên tắc thay thế

**Phân tích:**

1. **TokenService với cơ chế stateless JWT blacklist**
   - Thay đổi từ stateful JWT (lưu toàn bộ token) sang stateless JWT với blacklist (chỉ lưu token đã thu hồi)
   - Các phương thức mới vẫn đảm bảo cùng chức năng (quản lý token) nhưng theo cơ chế khác
   
   **Lợi ích**: Thay đổi cách thức triển khai mà không ảnh hưởng đến hành vi mong đợi của hệ thống (authenticate vẫn hoạt động đúng).
   
   **Nếu không áp dụng**: Thay đổi triển khai sẽ đòi hỏi sửa đổi nhiều nơi sử dụng dịch vụ.

2. **Sử dụng Interface**
   - `UserDomainService` và `UserDomainServiceImpl`
   - `ProfileMapper` và các implementation
   - `IJwtService` và `JwtService`
   - `ITokenService` và `TokenService`
   
   **Lợi ích**: Đảm bảo tính thay thế, các implementation có thể thay thế lẫn nhau.
   
   **Nếu không áp dụng**: Code phụ thuộc vào implementation cụ thể, khó thay thế và test.

3. **Inheritance trong Entity**
   - `User` implement `UserDetails` của Spring Security
   
   **Lợi ích**: User có thể được sử dụng ở bất kỳ đâu yêu cầu UserDetails.
   
   **Nếu không áp dụng**: Phải tạo adapter, tăng độ phức tạp, khó bảo trì.

4. **LogoutHandler Interface**
   - `LogoutService` implements `LogoutHandler` 
   
   **Lợi ích**: Đảm bảo tính thay thế, Spring Security có thể sử dụng bất kỳ implementation nào của LogoutHandler.
   
   **Nếu không áp dụng**: Phải custom SecurityConfig để sử dụng logout service.

### 4. Interface Segregation Principle (ISP) - Nguyên tắc phân tách interface

**Phân tích:**

1. **Interface Repository**
   - Mỗi repository chỉ chứa các phương thức cần thiết cho entity tương ứng
   - `BlacklistedTokenRepository` chỉ có các method `findByToken()` và `existsByToken()`
   
   **Lợi ích**: Không buộc implementation phải triển khai phương thức không dùng đến.
   
   **Nếu không áp dụng**: Phải triển khai các phương thức không cần thiết, tăng độ phức tạp.

2. **Interface ProfileMapper**
   - Chỉ chứa 2 phương thức cần thiết: `supports()` và `mapProfile()`
   
   **Lợi ích**: Interface gọn nhẹ, dễ implement.
   
   **Nếu không áp dụng**: Interface cồng kềnh, implementation phải cài đặt nhiều phương thức không cần thiết.

3. **Interface UserDomainService**
   - Tách biệt rõ ràng các phương thức domain logic
   
   **Lợi ích**: Client chỉ phụ thuộc vào những phương thức cần thiết.
   
   **Nếu không áp dụng**: Interface phình to, không rõ ràng về trách nhiệm.

4. **LogoutHandler Interface**
   - Spring Security cung cấp interface LogoutHandler với một phương thức duy nhất `logout()`
   
   **Lợi ích**: Interface tập trung vào một chức năng duy nhất, dễ implement.
   
   **Nếu không áp dụng**: LogoutService phải implement nhiều phương thức không liên quan.

5. **IJwtService Interface**
   - Chỉ chứa các phương thức JWT cần thiết cho việc xác thực và tạo token
   
   **Lợi ích**: Client chỉ phụ thuộc vào các phương thức JWT cần thiết, dễ implement và test.
   
   **Nếu không áp dụng**: Implementation phải cài đặt các phương thức không cần thiết.

6. **ITokenService Interface**
   - Chỉ chứa 3 phương thức cần thiết cho việc quản lý token: blacklist, check và generate
   
   **Lợi ích**: Interface gọn nhẹ, tập trung vào chức năng quản lý token.
   
   **Nếu không áp dụng**: Interface lớn khó quản lý, có thể phải implement nhiều phương thức không liên quan.

### 5. Dependency Inversion Principle (DIP) - Nguyên tắc đảo ngược phụ thuộc

**Phân tích:**

1. **Dependency Injection**
   - Sử dụng constructor injection trong tất cả các service và controller
   - Dùng annotation `@RequiredArgsConstructor` của Lombok kết hợp với `final` fields
   
   **Lợi ích**: Giảm sự phụ thuộc giữa các thành phần, dễ test và mở rộng.
   
   **Nếu không áp dụng**: Các class phụ thuộc trực tiếp vào implementation, khó test và thay thế.

2. **Phụ thuộc vào abstraction**
   - `UserService` phụ thuộc vào `UserMapper` (abstraction)
   - `UserMapper` phụ thuộc vào `List<ProfileMapper>` (abstraction)
   - `LogoutService` phụ thuộc vào `ITokenService` (abstraction) thay vì `TokenService` (concrete)
   - `JwtAuthenticationFilter` phụ thuộc vào `IJwtService` và `ITokenService` (abstractions)
   - `AuthenticationService` phụ thuộc vào `IJwtService` và `ITokenService` (abstractions)
   
   **Lợi ích**: Code linh hoạt, dễ thay đổi implementation mà không ảnh hưởng logic nghiệp vụ.
   
   **Nếu không áp dụng**: Code cứng nhắc, khó thay đổi implementation.

## Design Pattern đã áp dụng

### 1. Strategy Pattern
- **Vị trí áp dụng**: `ProfileMapper` và các implementation
- **Cách áp dụng**: `UserMapper` chọn mapper phù hợp dựa trên role của user
- **Lợi ích**: Dễ dàng thêm logic mapping cho loại user mới mà không sửa code hiện tại
- **Nếu không áp dụng**: Logic if-else phức tạp, khó bảo trì khi thêm role mới

### 2. Repository Pattern
- **Vị trí áp dụng**: Các class `UserRepository`, `BlacklistedTokenRepository`, v.v.
- **Cách áp dụng**: Sử dụng Spring Data JPA để truy cập dữ liệu
- **Lợi ích**: Tách biệt logic truy cập dữ liệu khỏi logic nghiệp vụ
- **Nếu không áp dụng**: Logic truy cập DB lẫn lộn với logic nghiệp vụ, khó bảo trì

### 3. DTO Pattern
- **Vị trí áp dụng**: `UserDto`, `AuthResponse`, `RegisterRequest`, v.v.
- **Cách áp dụng**: Sử dụng DTO để truyền dữ liệu giữa client và server
- **Lợi ích**: Bảo vệ dữ liệu, chỉ hiển thị thông tin cần thiết cho client
- **Nếu không áp dụng**: Lộ thông tin nhạy cảm, khó kiểm soát dữ liệu truyền qua API

### 4. Builder Pattern
- **Vị trí áp dụng**: Trong các entity và DTO, cụ thể trong BlacklistedToken khi tạo token mới
- **Cách áp dụng**: Sử dụng annotation `@Builder` của Lombok
- **Lợi ích**: Dễ dàng tạo object phức tạp, code rõ ràng, dễ đọc
- **Nếu không áp dụng**: Constructor phình to, khó đọc và bảo trì

### 5. Dependency Injection
- **Vị trí áp dụng**: Tất cả service, controller, filter
- **Cách áp dụng**: Constructor injection với `@RequiredArgsConstructor`
- **Lợi ích**: Giảm coupling, dễ test, dễ thay thế implementation
- **Nếu không áp dụng**: Các class phụ thuộc chặt chẽ vào nhau, khó test và mở rộng

### 6. Chain of Responsibility
- **Vị trí áp dụng**: `JwtAuthenticationFilter` và security filters
- **Cách áp dụng**: Chuỗi các filter xử lý request HTTP
- **Lợi ích**: Tách biệt các bước xử lý request, dễ thêm/bớt filter
- **Nếu không áp dụng**: Logic xử lý request tập trung, khó bảo trì và mở rộng

### 7. Facade Pattern
- **Vị trí áp dụng**: `TokenService` đóng vai trò facade cho các thao tác liên quan đến token
- **Cách áp dụng**: `TokenService` cung cấp API đơn giản cho các service khác như `LogoutService`
- **Lợi ích**: Đơn giản hóa giao diện cho client, ẩn độ phức tạp của việc quản lý token
- **Nếu không áp dụng**: Mỗi service phải tự xử lý việc tương tác với repository

### 8. Singleton Pattern
- **Vị trí áp dụng**: Các Spring bean (`@Service`, `@Component`)
- **Cách áp dụng**: Spring tạo và quản lý các bean như singleton 
- **Lợi ích**: Tiết kiệm tài nguyên, đảm bảo các bean được dùng chung
- **Nếu không áp dụng**: Tạo mới instance mỗi lần sử dụng, tốn tài nguyên

### 9. Interface-Based Programming
- **Vị trí áp dụng**: `IJwtService`, `ITokenService`, `ProfileMapper`
- **Cách áp dụng**: Tạo interface cho các service, tách biệt giữa interface và implementation
- **Lợi ích**: Dễ dàng thay đổi implementation, dễ test, tuân thủ DIP
- **Nếu không áp dụng**: Khó test và thay thế, vi phạm DIP

## Các cải tiến đã thực hiện

### 1. Giải quyết trùng lặp logic mapping
- **Vấn đề ban đầu**: Logic `mapToUserDto()` xuất hiện ở cả `UserService` và `AuthenticationService`
- **Giải pháp**: Đã tách ra mapper riêng (`UserMapper`) và inject vào cả hai service
- **Lợi ích**: Giảm trùng lặp code, tuân thủ SRP, dễ dàng thêm logic mapping mới

### 2. Áp dụng Factory Pattern cho profile
- **Vấn đề ban đầu**: Method `register()` có logic if-else phân biệt role
- **Giải pháp**: Đã tạo interface `ProfileFactory` và các implementation cho từng role
- **Lợi ích**: Tuân thủ OCP, dễ dàng thêm role mới mà không sửa code cũ

### 3. Thống nhất Exception handling
- **Vấn đề ban đầu**: Một số nơi throw raw exception, một số nơi dùng custom exception
- **Giải pháp**: Đã tạo và sử dụng các custom exception (`UserNotFoundException`, `AuthenticationException`)
- **Lợi ích**: Xử lý lỗi nhất quán, rõ ràng hơn cho client, dễ dàng xử lý tập trung với `GlobalExceptionHandler`

### 4. Tách biệt Token management và áp dụng stateless JWT với blacklist
- **Vấn đề ban đầu**: 
  - Logic quản lý token nằm trong `AuthenticationService`
  - Token được lưu đầy đủ trong DB (stateful JWT) dẫn đến tốn tài nguyên DB
- **Giải pháp**: 
  - Đã tách ra `TokenService` riêng biệt
  - Chuyển sang stateless JWT với blacklist (chỉ lưu token đã bị thu hồi)
  - Thêm `BlacklistedToken` entity và `BlacklistedTokenRepository`
  - Cập nhật `JwtAuthenticationFilter` để kiểm tra token trong blacklist
- **Lợi ích**: 
  - Tuân thủ SRP: `TokenService` tập trung vào quản lý token với blacklist, `JwtAuthenticationFilter` tập trung xác thực
  - Hiệu năng cao hơn: Giảm tải DB, chỉ lưu token đã revoke
  - Dễ dàng mở rộng (OCP): Có thể thêm logic blacklist mới mà không ảnh hưởng đến xác thực

### 5. Cải thiện LogoutService với blacklist token
- **Vấn đề ban đầu**:
  - LogoutService sử dụng TokenRepository và thao tác trực tiếp với entity Token
  - Cần tìm token, rồi gọi storedToken.revoke(), làm phức tạp logic xử lý
- **Giải pháp**:
  - Cập nhật LogoutService để sử dụng TokenService
  - Sử dụng phương thức blacklistToken() để xử lý token thu hồi
- **Lợi ích**:
  - Tuân thủ SRP: LogoutService chỉ tập trung vào logic đăng xuất
  - Tuân thủ DIP: Phụ thuộc vào abstraction (Service) thay vì implementation cụ thể (Repository)
  - Code đơn giản, dễ đọc và bảo trì hơn
  - Không cần xử lý null check cho storedToken

### 6. Áp dụng Dependency Inversion Principle với Service Interface
- **Vấn đề ban đầu**:
  - JwtAuthenticationFilter, LogoutService, AuthenticationService phụ thuộc vào implementation của JwtService và TokenService
  - Vi phạm DIP, khó thay đổi implementation và test
- **Giải pháp**:
  - Tạo interfaces IJwtService và ITokenService
  - Cập nhật các service để implement các interface tương ứng
  - Cập nhật các client để phụ thuộc vào interface thay vì implementation
- **Lợi ích**:
  - Tuân thủ DIP: High-level modules phụ thuộc vào abstraction không phải implementation
  - Dễ test hơn: Có thể tạo mock cho các dependencies
  - Mở rộng dễ dàng: Có thể thay đổi implementation mà không ảnh hưởng đến client
  - Giảm coupling, tăng cohesion: Các modules gắn kết lỏng lẻo với nhau

### 7. Tạo Integration tests toàn diện
- **Vấn đề ban đầu**:
  - Thiếu tests để xác nhận các luồng xử lý hoạt động đúng
  - Chưa có cách để kiểm tra token blacklisting có hoạt động đúng khi triển khai
- **Giải pháp**:
  - Đã tạo bộ integration tests toàn diện với Testcontainers và MySQL
  - Tự động kiểm tra toàn bộ luồng từ đăng ký, đăng nhập, logout, token blacklisting
  - Tests bao phủ các trường hợp lỗi và bảo mật
- **Lợi ích**:
  - Phát hiện lỗi sớm: Tests giúp phát hiện các vấn đề trước khi triển khai production
  - Tài liệu sống: Tests mô tả cách module hoạt động, giúp developers mới hiểu hệ thống
  - Tự tin khi thay đổi: Có thể tái cấu trúc code mà không lo lắng phá vỡ chức năng
  - Kiểm tra tích hợp: Đảm bảo các thành phần đã tích hợp với nhau hoạt động đúng đắn

## Kết luận

Module User trong hospital-management-system đã được cải thiện đáng kể thông qua việc áp dụng các nguyên tắc SOLID và design pattern. Các cải tiến chính bao gồm:

1. **Giảm trùng lặp code** thông qua các mapper tách riêng
2. **Tăng tính mở rộng** nhờ Factory Pattern và các interface phù hợp
3. **Xử lý lỗi nhất quán** với các custom exception
4. **Phân tách trách nhiệm** rõ ràng giữa các service, factory, mapper
5. **Tối ưu hóa quản lý token** bằng stateless JWT với blacklist
6. **Cải thiện LogoutService** để sử dụng TokenService thay vì trực tiếp thao tác với repository
7. **Áp dụng DIP triệt để** thông qua việc tạo interfaces cho các service và chuyển dependencies từ implementation sang abstraction
8. **Tạo bộ integration tests toàn diện** để đảm bảo chất lượng khi triển khai và phát triển

Việc áp dụng các nguyên tắc SOLID đã mang lại nhiều lợi ích:

- **Dễ bảo trì**: Mỗi class chỉ có một trách nhiệm duy nhất
- **Dễ mở rộng**: Có thể thêm tính năng mới mà không thay đổi code cũ
- **Dễ test**: Mỗi đơn vị nhỏ gọn, dễ test riêng lẻ
- **Dễ đọc**: Code rõ ràng, logic rạch ròi, giúp dev mới dễ onboard
- **Dễ thay thế**: Các implementation có thể được thay thế mà không ảnh hưởng đến client code
- **Chất lượng đảm bảo**: Integration tests giúp đảm bảo tất cả các thành phần hoạt động đúng như mong đợi

Những cải tiến này không chỉ tăng chất lượng code mà còn tăng khả năng ứng phó với các yêu cầu thay đổi trong tương lai, làm nền tảng vững chắc cho sự phát triển bền vững của phần mềm. Việc chuyển sang stateless JWT với blacklist đặc biệt đã cải thiện hiệu năng và tính mở rộng của hệ thống, đồng thời vẫn đảm bảo tính bảo mật. Cải thiện LogoutService là một bước tiến quan trọng trong việc hoàn thiện kiến trúc hệ thống theo các nguyên tắc SOLID. Việc áp dụng DIP thông qua việc tạo và sử dụng các interface là bước tiến lớn giúp giảm coupling, tăng tính mở rộng và dễ test. Cuối cùng, integration tests toàn diện giúp đảm bảo tất cả các thành phần hoạt động đúng đắn và tích hợp tốt với nhau, đặc biệt là các luồng quan trọng như đăng ký, đăng nhập, token refresh và logout.
