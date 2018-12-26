using System;
using System.Net.Http;
using System.Text;
using AlphaParking.BLL;
using AlphaParking.DbContext.Models;
using AlphaParking.Web.Host.Extensions;
using AlphaParking.Web.Host.Utils;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;

namespace AlphaParking.Web.Host
{   // TODO: дропать localStorage на клиенте на логауте
    public class Startup
    {
        public IConfiguration Configuration { get; }
        
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            string _defaultConnection = Configuration.GetConnectionString("DefaultConnection");
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddEntityFrameworkNpgsql().AddDbContext<AlphaParkingDbContext>(options => options.UseNpgsql(_defaultConnection), ServiceLifetime.Scoped);
            services.AddDbContextFactory(_defaultConnection);

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

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAnyOrigin",
                   builder => builder.AllowAnyOrigin()
                                     .AllowAnyMethod()
                                     .AllowAnyHeader()
                                     .AllowCredentials()
                                     .Build());
            });
            services.AddServices();
            services.AddDbRepositories();
            services.AddEventBus();
            services.AddConfiguratedAutoMapper();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }
            // app.UseHttpsRedirection();       
            //app.UseCors("AllowAnyOrigin");
            //app.UseErrorHandlerMiddleware();
            app.UseAuthentication();
            app.UseMvc();

            //SeedDbService.EnsurePopulated(app).Wait();
        }
    }
}
