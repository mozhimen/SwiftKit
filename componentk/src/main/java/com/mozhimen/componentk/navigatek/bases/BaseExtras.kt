package com.mozhimen.componentk.navigatek.bases

import android.view.View
import androidx.navigation.Navigator
import java.util.Collections


/**
 * @ClassName BaseExtras
 * @Description Extras that can be passed to FragmentNavigator to enable Fragment specific behavior
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/25 11:34
 * @Version 1.0
 */
open class BaseExtras(sharedElements: Map<View, String>) : Navigator.Extras {
    private val _sharedElements = LinkedHashMap<View, String>()

    init {
        _sharedElements.putAll(sharedElements)
    }

    /**
     * Gets the map of shared elements associated with these Extras. The returned map
     * is an [unmodifiable][Collections.unmodifiableMap] copy of the underlying
     * map and should be treated as immutable.
     */
    val sharedElements: Map<View, String>
        get() = _sharedElements.toMap()

    /**
     * Builder for constructing new [Extras] instances. The resulting instances are
     * immutable.
     */
    class Builder {
        private val _sharedElements = LinkedHashMap<View, String>()

        /**
         * Adds multiple shared elements for mapping Views in the current Fragment to
         * transitionNames in the Fragment being navigated to.
         *
         * @param sharedElements Shared element pairs to add
         * @return this [Builder]
         */
        fun addSharedElements(sharedElements: Map<View, String>): Builder {
            for ((view, name) in sharedElements) {
                addSharedElement(view, name)
            }
            return this
        }

        /**
         * Maps the given View in the current Fragment to the given transition name in the
         * Fragment being navigated to.
         *
         * @param sharedElement A View in the current Fragment to match with a View in the
         * Fragment being navigated to.
         * @param name          The transitionName of the View in the Fragment being navigated to that
         * should be matched to the shared element.
         * @return this [Builder]
         * @see FragmentTransaction.addSharedElement
         */
        fun addSharedElement(sharedElement: View, name: String): Builder {
            _sharedElements[sharedElement] = name
            return this
        }

        /**
         * Constructs the final [Extras] instance.
         *
         * @return An immutable [Extras] instance.
         */
        fun build(): BaseExtras {
            return BaseExtras(_sharedElements)
        }
    }
}