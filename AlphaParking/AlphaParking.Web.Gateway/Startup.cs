﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using Ocelot.DependencyInjection;
using Ocelot.Middleware;

namespace AlphaParking.Web.Gateway
{
    public class Startup 
    { 
        public Startup(IHostingEnvironment env) 
        { 
            var builder = new ConfigurationBuilder(); 
            builder.SetBasePath(env.ContentRootPath);
            builder.AddJsonFile($"configuration.{env.EnvironmentName}.json", true, true);
            builder.AddEnvironmentVariables();

            Configuration = builder.Build(); 
        } 

        public IConfigurationRoot Configuration { get; } 

        public void ConfigureServices(IServiceCollection services) 
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);

            var tokenValidationParameters = new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(AppConsts.TokenSecretPass)),
                ValidateIssuer = true,
                ValidIssuer = AppConsts.TokenIssuer,
                RequireExpirationTime = true,
                ValidateAudience = false
            };

            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(x =>  
                    {  
                        x.RequireHttpsMetadata = false;  
                        x.TokenValidationParameters = tokenValidationParameters;  
                    });  
 
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
            app.UseAuthentication();
            app.UseMvc();
        } 
    }
}
