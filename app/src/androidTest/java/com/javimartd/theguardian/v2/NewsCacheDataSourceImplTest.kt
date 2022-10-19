package com.javimartd.theguardian.v2

import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import junit.framework.TestCase
import org.junit.Before

//@RunWith(AndroidJUnit4::class)
class NewsCacheDataSourceImplTest: TestCase() {

    private lateinit var sut : NewsCacheDataSource

    @Before
    fun setup() {
        //sut = NewsCacheDataSourceImpl()
    }

    /*@Test
    fun `get sections`()
    = runBlocking {

        // when
        val actual = sut.getSections()

        // then
        Assert.assertEquals(actual, emptyList<SectionRaw>())
    }*/

    /*@Test
    fun `save sections`() {

        // given
        val data = DataFactory.makeSections(2)

        runBlocking {
            // when
            sut.saveSections(data)
            val actual = sut.getSections()

            // then
            Assert.assertEquals(2, actual.size)
            MatcherAssert.assertThat(actual[0], IsInstanceOf.instanceOf(SectionRaw::class.java))
        }
    }*/
}