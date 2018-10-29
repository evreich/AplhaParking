using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Services.DTO
{
    public class AuthInfo
    {
        public string Name { get; set; }
        public string Login { get; set; }
        public string Token { get; set; }
        public string[] Roles { get; set; }
    }
}
