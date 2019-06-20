package defy.tech.chickenlover.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel;

class HomeViewModel : DisposableViewModel() {
    var info_name = ObservableField<String>()
    var info_brand = ObservableField<String>()

    init {
        info_name.set("선택해주세요")
        info_brand.set("선택해주세요")
    }
}
