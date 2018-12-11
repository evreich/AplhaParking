using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using AlphaParking.BLL;
using AlphaParking.BLL.DTO.MapperProfiles;
using AlphaParking.DAL;
using AlphaParking.DAL.UnitOfWork;
using AlphaParking.DbContext.Models;
using AlphaParking.Web.Host.Extensions;
using AutoMapper;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using RabbitMQ.Client;

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
            services.AddAutoMapper(typeof(UserProfile).GetTypeInfo().Assembly, typeof(CarProfile).GetTypeInfo().Assembly, 
                typeof(ViewModels.MapperProfiles.CarProfile).GetTypeInfo().Assembly, typeof(ViewModels.MapperProfiles.UserProfile).GetTypeInfo().Assembly);
            services.AddSingleton<Func<AlphaParkingDbContext>>(() =>
            {
                var optionsBuilder = new DbContextOptionsBuilder<AlphaParkingDbContext>();
                optionsBuilder.UseNpgsql(_defaultConnection);
                return new AlphaParkingDbContext(optionsBuilder.Options);
            });
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddEntityFrameworkNpgsql().AddDbContext<AlphaParkingDbContext>(options => options.UseNpgsql(_defaultConnection), ServiceLifetime.Scoped);
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
