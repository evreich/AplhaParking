using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Ocelot.DependencyInjection;
using Ocelot.Middleware;

namespace AlphaParking.Web.Gateway
{
    public class Startup 
    { 
        public Startup(IHostingEnvironment env) 
        { 
            var builder = new ConfigurationBuilder(); 
            builder.SetBasePath(env.ContentRootPath) 
            .AddJsonFile("configuration.json", optional: false, reloadOnChange: true)
            .AddEnvironmentVariables(); 

            Configuration = builder.Build(); 
        } 

        //change 
        public IConfigurationRoot Configuration { get; } 

        public void ConfigureServices(IServiceCollection services) 
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddOcelot(Configuration); 
            services.AddCors(options =>
            {
                options.AddPolicy("CorsPolicy",
                    builder => builder.AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader()
                        .AllowCredentials() 
                );
            });

            services.AddOptions();
        } 

        public void Configure(IApplicationBuilder app, IHostingEnvironment env) 
        {
            app.UseCors("CorsPolicy");
            app.UseOcelot().Wait();
            app.UseMvc();
        } 
    }
}
