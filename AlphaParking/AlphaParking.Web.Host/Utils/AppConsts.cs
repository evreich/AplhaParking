using System.Collections.Generic;
using static AlphaParking.Web.Host.Utils.AuthServiceClient;

namespace AlphaParking.Web.Host.Utils
{
    public static class AppConsts
    {
        public static readonly string ManagerRole;
        public static readonly string EmployeeRole;
        public static string TokenIssuer = "AlphaParkingAuthService";
        public static string TokenSecretPass = "SuperSecretAlphaParkingKey321";

        //static AppConsts()
        //{
        //    AuthServiceClient authServiceClient = new AuthServiceClient(new System.Net.Http.HttpClient());
        //    List<Role> roles = authServiceClient.GetRoles().Result;
        //    if(roles != null)
        //    {
        //        ManagerRole = roles.Find(role => role.id == 1).name;
        //        EmployeeRole = roles.Find(role => role.id == 2).name;
        //    }
        //}
    }
}