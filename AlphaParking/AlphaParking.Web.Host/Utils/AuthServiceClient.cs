
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.Utils
{
    public class AuthServiceClient
    {
        private HttpClient _httpClient;
        private readonly string GATEWAY = Environment.GetEnvironmentVariable("GATEWAY_HOST") == null ?
            "http://localhost:9000" :
            Environment.GetEnvironmentVariable("GATEWAY_HOST") + ':' + Environment.GetEnvironmentVariable("GATEWAY_PORT");

        public AuthServiceClient(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public class Role
        {
            public int id;
            public string name;

            public Role(int id, string name)
            {
                this.id = id;
                this.name = name;
            }
        }

        public async Task<List<Role>> GetRoles()
        {
            _httpClient.DefaultRequestHeaders.Clear();
            _httpClient.BaseAddress = new Uri(GATEWAY);
            // TODO: _httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {jwt}"); 
            // TODO: ождиание запуска JavaAuth для получения ролей
            var response = await _httpClient.GetAsync("/roles");
            if (response.IsSuccessStatusCode)
            {
                return (await response.Content.ReadAsAsync<List<Role>>());
            }
            else
            {
                return null;
            }
        }
    }
}