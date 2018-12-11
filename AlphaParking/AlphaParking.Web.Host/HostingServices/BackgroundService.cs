using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.HostingServices
{
    public abstract class BackgroundService : IHostedService
    {
        private readonly IServiceScopeFactory _serviceScopeFactory;

        public BackgroundService(IServiceScopeFactory serviceScopeFactory)
        {
            _serviceScopeFactory = serviceScopeFactory;
        }

        public Task StartAsync(CancellationToken cancellationToken)
        {
            DoWork();

            return Task.CompletedTask;
        }

        private void DoWork()
        {
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                ProcessInScope(scope.ServiceProvider);
            }
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            return Task.CompletedTask;
        }

        public abstract void ProcessInScope(IServiceProvider serviceProvider);
    }
}
