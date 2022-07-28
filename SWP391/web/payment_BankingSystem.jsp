<label for="payment" class="form-label" ></label>
<textarea  readonly   style="background:#ffffff;"
           class="guidline form-control " id="payment" form="checkout" name="payment" rows="3">
    Customers pay in advance for orders through the account number below.
    Sales staff will activate the order when receiving the money.
    Bank system:${initParam['banking_system']}
    Account holder:${initParam['account_holder']}
    Bank Account number:${initParam['account_number']}
    Content:'Your email' +'your name' + Payment orders
</textarea>