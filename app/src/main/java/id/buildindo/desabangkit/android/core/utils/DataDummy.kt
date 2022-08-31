package id.buildindo.desabangkit.android.core.utils

import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.Roles

object DataDummy {
    fun getRolesData() : List<Roles> {
        val rolesList = ArrayList<Roles>()
        rolesList.add(
            Roles(
                "62fca80d573fe35e29e1c413",
                "Partner",
                "Orang yang berkontribusi dalam menghasilkan pangan segar, seperti peternak, petani, dan nelayan",
                R.drawable.partner_roles
            )
        )
        rolesList.add(
            Roles(
                "62fca770573fe35e29e1c408",
                "Konsumen",
                "Orang yang membeli hasil dari pangan segar yang akan diolah menjadi bahan olahan",
                R.drawable.konsumen_roles
            )
        )
    return rolesList
    }
}