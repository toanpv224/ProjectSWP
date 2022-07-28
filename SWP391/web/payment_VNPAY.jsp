<div style="position: relative">
<label for="payment" class="form-label" ></label>
<textarea  readonly   style="background:#ffffff;"
           class="guidline form-control " id="payment" form="checkout" name="payment" rows="3">
    The system will show you QR CODE
    And you login vnpay app in mobile 
    To scan QR for payment
</textarea>
<button style="position: absolute; bottom:10px;
  left: 20px;"
        type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
      Start
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Scan QR code By your Phone</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
              <div class="qrcode">
                  <img src="${initParam['vnpay_qr_code']}" />
              </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="button" onclick="document.getElementById('pay-ment').submit()" class="btn btn-primary">I scanned and paid</button>
          </div>
        </div>
      </div>
    </div>
</div>