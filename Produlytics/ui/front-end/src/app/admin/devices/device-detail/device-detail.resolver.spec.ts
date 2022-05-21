import {TestBed} from "@angular/core/testing";
import {FindDeviceService} from "../../../model/admin-device/find-detail/find-device.service";
import {FindDeviceAbstractService} from "../../../model/admin-device/find-detail/find-device-abstract.service";
import {DeviceDetailResolver} from "./device-detail.resolver";
import {testModules} from "../../../test/utils";
import {ActivatedRouteSnapshot, Router} from "@angular/router";
import {HttpTestingController} from "@angular/common/http/testing";
import {valvolaDevice} from "../../../test/device/fake-device.service";

describe('DeviceDetailResolver Integration', () => {

  let resolver: DeviceDetailResolver;
  let router: Router;
  let route: ActivatedRouteSnapshot;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [],
      providers: [
        FindDeviceService,
        {
          provide: FindDeviceAbstractService,
          useExisting: FindDeviceService
        },
        DeviceDetailResolver,
        {
          provide: ActivatedRouteSnapshot,
          useValue: {
            params: {
              id: 1
            }
          }
        }
      ]
    })
      .compileComponents();

    resolver = TestBed.inject(DeviceDetailResolver);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRouteSnapshot);
    httpTestingController = TestBed.inject(HttpTestingController);
  })

  it('device-detail-resolve', async () => {
    resolver.resolve(route).subscribe(value => {
      expect(value.id).toEqual(1);
    });
    const req = httpTestingController.expectOne('admin/devices/1');
    expect(req.request.method).toEqual('GET');
    req.flush(valvolaDevice);
    httpTestingController.verify();
  });
});
