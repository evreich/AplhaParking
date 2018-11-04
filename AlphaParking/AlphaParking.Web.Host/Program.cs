using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL.Services;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace AlphaParking.Web.Host
{
    public class Program
    {
        static void Main(string[] args)
        {
            var host = CreateWebHostBuilder(args).Build();
            host.Services.GetRequiredService<ISeedDbService>().EnsurePopulated().GetAwaiter().GetResult();
            host.Run();
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
