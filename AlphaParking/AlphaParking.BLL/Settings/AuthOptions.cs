using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Settings
{
    public static class AuthOptions
    {
        public static readonly string ISSUER = "AlphaParkingAuthServer";
        const string KEY = "mysupersecret_secretkey!123";
        public static readonly int LIFETIME = 3;
        public static SymmetricSecurityKey GetSymmetricSecurityKey()
        {
            return new SymmetricSecurityKey(Encoding.ASCII.GetBytes(KEY));
        }
    }
}
