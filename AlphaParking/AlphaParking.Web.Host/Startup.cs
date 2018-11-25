using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL.Services;
using AlphaParking.DAL.Repositories;
using AlphaParking.DAL.Repositories.UnitOfWork;
using AlphaParking.DB.DbContext.Models;
using AlphaParking.Web.Host.Extensions;
using AutoMapper;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace AlphaParking.Web.Host
{   // TODO: приступить к контроллерам
    // filter attribute для валидации попробовать
    // настроить вебпак, сделать базу для запуска приложения реакт под ts
    // дропать localStorage на клиенте на логауте
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
            string _jwt_audience = Configuration.GetConnectionString("JWT_AUDIENCE");
            string _defaultConnection = Configuration.GetConnectionString("DefaultConnection");
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);

            services.AddDbContext<AlphaParkingDbContext>(options => options.UseSqlServer(_defaultConnection), ServiceLifetime.Scoped);
            services.AddAutoMapper();
            services.AddCors(options =>
            {
                options.AddPolicy("AllowAnyOrigin",
                   builder => builder.AllowAnyOrigin()
                                     .AllowAnyMethod()
                                     .AllowAnyHeader()
                                     .AllowCredentials()
                                     .Build());
            });
            services.AddJWTAuth(_jwt_audience);

            services.AddScoped<IUnitOfWork, UnitOfWork>();
            services.AddDbRepositories();
            services.AddServices(_jwt_audience);
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
            app.UseCors("AllowAnyOrigin");
            app.UseAuthentication();
            app.UseErrorHandlerMiddleware();
            app.UseMvc();

            SeedDbService.EnsurePopulated(app).Wait();
        }
    }
}
