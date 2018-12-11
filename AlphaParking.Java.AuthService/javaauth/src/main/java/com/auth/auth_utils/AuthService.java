// namespace AlphaParking.BLL
// {
//     public class AuthService: IAuthService
//     {
//         private readonly IUnitOfWork _database;
//         private readonly IUserService _userService;
//         private readonly string _audienceJWT;
//         private readonly IMapper _mapper;

//         public AuthService(IUnitOfWork uow, IUserService userService, string audience, IMapper mapper)
//         {
//             _database = uow;
//             _userService = userService;
//             _audienceJWT = audience;
//             _mapper = mapper;
//         }

//         public string GenerateJwtToken(UserDTO user)
//         {
//             var claims = new List<Claim>
//             {
//                 new Claim(ClaimTypes.Email, user.Email),
//                 new Claim(ClaimTypes.Sid, Guid.NewGuid().ToString()),
//                 new Claim(ClaimTypes.NameIdentifier, user.FIO),
//             };
//             claims.AddRange(user.Roles.Select(r => new Claim(ClaimTypes.Role, r.Name)).ToList());

//             var key = AuthOptions.GetSymmetricSecurityKey();
//             var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
//             var expires = DateTime.Now.AddHours(Convert.ToDouble(AuthOptions.LIFETIME));

//             var token = new JwtSecurityToken(
//                 AuthOptions.ISSUER,
//                 _audienceJWT,
//                 claims,
//                 expires: expires,
//                 signingCredentials: creds
//             );

//             return new JwtSecurityTokenHandler().WriteToken(token);
//         }

//         public async Task<AuthInfo> Login(string login, string password)
//         {
//             UserDTO user = _mapper.Map<UserDTO>(await _database.UserRepository.GetElem(u => u.Login.Equals(login)));
//             if (user == null)
//             {
//                 throw new ValidationException("Данный пользователь не зарегистрирован");
//             }
//             if (!await _userService.IsRegistered(login, password))
//             {
//                 throw new BadRequestException("Неверный пароль");
//             }

//             var userToken = this.GenerateJwtToken(user);

//             return new AuthInfo
//             {
//                 Name = user.FIO,
//                 Login = user.Login,
//                 Token = userToken,
//                 Roles = user.Roles.Select(r => r.Name).ToArray()
//             };
//         }

//         public async Task<AuthInfo> Registration(UserDTO user)
//         {
//             UserDTO createdUser = await _userService.Create(user);
//             var userToken = this.GenerateJwtToken(createdUser);

//             return new AuthInfo
//             {
//                 Name = createdUser.FIO,
//                 Login = createdUser.Login,
//                 Token = userToken,
//                 Roles = createdUser.Roles.Select(r => r.Name).ToArray()
//             };
//         }

//         public void Dispose()
//         {
//             _database.Dispose();
//         }
//     }
// }

// Настройки на уровне Spring Boot  в похожем ключе

// services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
// .AddJwtBearer(options =>
// {

//     options.RequireHttpsMetadata = false;
//     options.TokenValidationParameters = new TokenValidationParameters
//     {
//         // укзывает, будет ли валидироваться издатель при валидации токена
//         ValidateIssuer = true,
//         // строка, представляющая издателя
//         ValidIssuer = AuthOptions.ISSUER,

//         // будет ли валидироваться потребитель токена
//         ValidateAudience = true,
//         // установка потребителя токена
//         ValidAudience = jwt_audience,
//         // будет ли валидироваться время существования
//         ValidateLifetime = true,
//         // установка ключа безопасности
//         IssuerSigningKey = AuthOptions.GetSymmetricSecurityKey(),
//         // валидация ключа безопасности
//         ValidateIssuerSigningKey = true,
//         ClockSkew = TimeSpan.Zero,
//     };
// });